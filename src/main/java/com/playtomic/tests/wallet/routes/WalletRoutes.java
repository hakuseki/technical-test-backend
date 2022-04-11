package com.playtomic.tests.wallet.routes;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletRequest;
import com.playtomic.tests.wallet.model.WalletTopupRequest;
import com.playtomic.tests.wallet.service.Payment;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;
import org.apache.camel.Message;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.jdbc.JdbcOutputType;
import org.h2.jdbc.JdbcSQLDataException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

//tag::WalletRoutes[]

/**
 * The class WalletRoutes
 *
 * @author maw, 2022-04-09
 * @version 1.0
 */
@Component
public class WalletRoutes extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(JdbcSQLDataException.class, StripeServiceException.class)
                .handled(true)
                .transform()
                .simple(null)
                .to(direct("exceptionHandled"));

        from(direct("exceptionHandled"))
                .log("${body}")
                .end();

        //tag::wallet[]
        from(direct("wallet"))
                .routeId("WALLET-ROUTE")
                .description("This is a wallet route")
                .process(exchange -> { //<.>
                    final Message in = exchange.getIn();
                    final WalletRequest walletRequest = in.getBody(WalletRequest.class);

                    final Optional<String> walletIdOpt = Optional.ofNullable(walletRequest.getWalletId());
                    walletIdOpt.ifPresentOrElse(s -> in.setHeader("walletId", s), () -> in.setBody(null));
                })
                .choice()
                .when(body().isNotNull())
                .setBody(simple("select * from wallet where wallet_id = :?walletId")) //<.>
                .toD(jdbc("default").outputClass(Wallet.class.getName()) //<.>
                                    .outputType(JdbcOutputType.SelectOne)
                                    .useHeadersAsParameters(true))
                .log("${body}")
                .end();
        //end::wallet[]

        //tag::wallet-topup[]
        from(direct("wallet-topup"))
                .routeId("WALLET_TOPUP")
                .description("Topup a wallet with funds")
                .process(exchange -> { //<.>
                    final Message in = exchange.getIn();
                    final WalletTopupRequest walletTopupRequest = in.getBody(WalletTopupRequest.class);
                    final Optional<String> walletIdOpt =
                            Optional.ofNullable(walletTopupRequest.getWalletId());
                    walletIdOpt.ifPresentOrElse(s -> {
                        in.setHeader("walletId", s);
                        in.setBody(new WalletRequest(s), WalletRequest.class);
                    }, () -> in.setBody(null));

                    final Optional<BigDecimal> amountOpt = Optional.ofNullable(walletTopupRequest.getAmount());
                    amountOpt.ifPresentOrElse(s -> {
                        in.setHeader("topUpAmount", s);
                    }, () -> in.setBody(null));

                    final Optional<String> cardNumberOpt =
                            Optional.ofNullable(walletTopupRequest.getCardNumber());
                    cardNumberOpt.ifPresentOrElse(s -> in.setHeader("cardNumber", s), () -> in.setBody(null));
                })
                .choice()
                .when(body().isNotNull())
                .to(direct("wallet")) //<.>
                .end()
                .choice()
                .when(body().isNotNull())
                .setHeader("wallet", simple("${body}")) //<.>
                .bean(StripeService.class, "charge(${header.cardNumber}, ${header.topUpAmount})") //<.>
                .end()
                .choice()
                .when(body().isNotNull())
                .log("${body}")
                .process(exchange -> { //<.>
                    final Message in = exchange.getIn();
                    final Payment payment = in.getBody(Payment.class);
                    final Wallet wallet = in.getHeader("wallet", Wallet.class);
                    wallet.setBalance(wallet.getBalance()
                                            .add(payment.getAmount()));
                    in.setBody(wallet, Wallet.class);
                })
                .toD(jpa(Wallet.class.getName()).useExecuteUpdate(true)) //<.>
                .log("${body}")
                .end();
        //end::wallet-topup[]
    }
}
//end::WalletRoutes[]
