package com.playtomic.tests.wallet.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * The type Payment.
 */
@ToString
@Getter
@Setter
public class Payment {

    /**
     * The Id.
     */
    @NonNull
    private final String id;

    /**
     * The Amount.
     */
    private BigDecimal amount;

    /**
     * Instantiates a new Payment.
     *
     * @param id the id
     */
    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) final String id) {
        this.id = id;
    }
}
