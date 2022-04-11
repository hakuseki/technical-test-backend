package com.playtomic.tests.wallet.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;


/**
 * Handles the communication with Stripe.
 * <p>
 * A real implementation would call to String using their API/SDK.
 * This dummy implementation throws an error when trying to charge less than 10€.
 */
@Service
public class StripeService {

    /**
     * The Charges uri.
     */
    @NonNull
    private final URI chargesUri;

    /**
     * The Refunds uri.
     */
    @NonNull
    private final URI refundsUri;

    /**
     * The Rest template.
     */
    @NonNull
    private final RestTemplate restTemplate;

    /**
     * Instantiates a new Stripe service.
     *
     * @param chargesUri          the charges uri
     * @param refundsUri          the refunds uri
     * @param restTemplateBuilder the rest template builder
     */
    public StripeService(@Value("${stripe.simulator.charges-uri}") @NonNull final URI chargesUri,
                         @Value("${stripe.simulator.refunds-uri}") @NonNull final URI refundsUri,
                         @NonNull final RestTemplateBuilder restTemplateBuilder) {
        this.chargesUri = chargesUri;
        this.refundsUri = refundsUri;
        this.restTemplate =
                restTemplateBuilder
                        .errorHandler(new StripeRestTemplateResponseErrorHandler())
                        .build();
    }

    /**
     * Charges money in the credit card.
     * <p>
     * Ignore the fact that no CVC or expiration date are provided.
     *
     * @param creditCardNumber The number of the credit card
     * @param amount           The amount that will be charged.
     * @return the payment
     * @throws StripeServiceException the stripe service exception
     */
    public Payment charge(@NonNull final String creditCardNumber, @NonNull final BigDecimal amount) throws
                                                                                                    StripeServiceException {
        final ChargeRequest body = new ChargeRequest(creditCardNumber, amount);
        return restTemplate.postForObject(chargesUri, body, Payment.class);
    }

    /**
     * Refunds the specified payment.
     *
     * @param paymentId the payment id
     * @throws StripeServiceException the stripe service exception
     */
    public void refund(@NonNull final String paymentId) throws StripeServiceException {
        // Object.class because we don't read the body here.
        restTemplate.postForEntity(chargesUri.toString(), null, Object.class, paymentId);
    }

    /**
     * The type Charge request.
     */
    @AllArgsConstructor
    private static class ChargeRequest {

        /**
         * The Credit card number.
         */
        @NonNull
        @JsonProperty("credit_card")
        String creditCardNumber;

        /**
         * The Amount.
         */
        @NonNull
        @JsonProperty("amount")
        BigDecimal amount;
    }
}
