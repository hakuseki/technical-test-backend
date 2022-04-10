package com.playtomic.tests.wallet.routes;

import com.playtomic.tests.wallet.model.Wallet;
import org.apache.camel.Message;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.jdbc.JdbcOutputType;
import org.h2.jdbc.JdbcSQLDataException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

//tag::WalletRoutes[]

/**
 * The class WalletRoutes
 *
 * @author maw, (c) Compliance Solutions Strategies, 2022-04-09
 * @version 1.0
 */
@Component
public class WalletRoutes extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        errorHandler(defaultErrorHandler());

        onException(JdbcSQLDataException.class)
                .handled(true)
                .setBody(simple(null))
                .to(mock("foo"));

        from(direct("wallet"))
                .routeId("WALLET-ROUTE")
                .description("This is a wallet route")
                .process(exchange -> {
                    final Message in = exchange.getIn();
                    final Map<String, Object> body = in.getBody(Map.class);
                    final Optional<String> walletId = Optional.of((String) body.getOrDefault("walletId", null));
                    walletId.ifPresentOrElse(s -> in.setBody(walletId.get()), () -> in.setBody(null));
                })
                .choice()
                .when(body().isNotNull())
                .setBody(simple("select * from wallet where wallet_Id = '${body}'"))
                .toD(jdbc("default").outputClass(Wallet.class.getName())
                                    .outputType(JdbcOutputType.SelectOne))
                .log("${body}")
                .end();
    }
}
//end::WalletRoutes[]
