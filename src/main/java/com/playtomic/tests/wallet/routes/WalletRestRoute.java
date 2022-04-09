package com.playtomic.tests.wallet.routes;

import com.playtomic.tests.wallet.model.Wallet;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
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
public class WalletRestRoute extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .apiContextPath("/api")
                .apiProperty("cors", "true");

        rest("/wallet")
                .consumes("application/json")
                .produces("application/json")
                .description("REST-WALLET", "This is a Wallet service", "en")
                .post()
                .outType(Wallet.class)
                .responseMessage()
                .code(200)
                .message("All records successfully returned")
                .endResponseMessage()
                .to("direct:wallet");

    }
}
//end::WalletRestRoute[]
