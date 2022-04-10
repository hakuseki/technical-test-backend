package com.playtomic.tests.wallet.routes;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.model.WalletRequest;
import com.playtomic.tests.wallet.model.WalletTopupRequest;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

//tag::WalletRestRoute[]

/**
 * The class WallterRoute
 *
 * @author maw, (c) Compliance Solutions Strategies, 2022-04-08
 * @version 1.0
 */
@Component
@CommonsLog
public class WalletRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .apiContextPath("/api")
                .apiProperty("api.title", "Wallet Service")
                .apiProperty("api.description", "API for querying wallet and adding funds")
                .apiProperty("api.version", "1.0.0")
                .apiProperty("api.contact.email", "mikael.andersson.wigander@pm.me")
                .apiProperty("api.contact.brokerUrl", "https://hmpg.net/")
                .apiProperty("api.license.name", "Copyright Wally Services")
                .apiProperty("cors", "true");

        rest("/wallet")
                .consumes("application/json")
                .produces("application/json")
                .description("REST-WALLET", "This is a Wallet service", "en")
                .post()
                .type(WalletRequest.class)
                .outType(Wallet.class)
//                .responseMessage()
//                .code(200)
//                .message("All records successfully returned")
//                .endResponseMessage()
                .to("direct:wallet")

                .post("/topup")
                .id("WALLET-TOPUP")
                .description("REST-WALLET-TOPUP", "Adding funds to the wallet using a credit card", "en")
                .type(WalletTopupRequest.class)
                .outType(Wallet.class)
//                .responseMessage()
//                .code(200)
//                .message("Wallet successfully returned")
//                .endResponseMessage()
                .to("direct:wallet-topup");

    }
}
//end::WalletRestRoute[]
