package com.playtomic.tests.wallet.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * The type Stripe rest template response error handler.
 */
public class StripeRestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    /**
     * Handle error.
     *
     * @param response   the response
     * @param statusCode the status code
     * @throws IOException the io exception
     */
    @Override
    protected void handleError(final ClientHttpResponse response, final HttpStatus statusCode) throws IOException {
        if (statusCode == HttpStatus.UNPROCESSABLE_ENTITY) {
            throw new StripeAmountTooSmallException();
        }

        super.handleError(response, statusCode);
    }
}
