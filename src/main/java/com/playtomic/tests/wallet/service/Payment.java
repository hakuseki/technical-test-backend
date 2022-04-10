package com.playtomic.tests.wallet.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class Payment {

    @NonNull
    private final String id;

    private BigDecimal amount;

    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) final String id) {
        this.id = id;
    }
}
