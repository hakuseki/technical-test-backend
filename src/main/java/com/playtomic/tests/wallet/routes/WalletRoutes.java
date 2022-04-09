package com.playtomic.tests.wallet.routes;

import com.playtomic.tests.wallet.model.Wallet;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

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
        from(direct("wallet"))
                .log("${body}")
                .process(exchange -> {
                    final Map<String,Object> body = exchange.getIn()
                                                            .getBody(Map.class);
                    exchange.getIn().setBody(body.get("walletId"));
                })
                .toD(jpa(Wallet.class.getName()).query("select x from " + Wallet.class.getName() + " x where x" +
                                                               ".walletId = '${body}'"))
                .log("${body}")
                .to(mock("whatever"));

    }
}
//end::WalletRoutes[]
