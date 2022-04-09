package com.playtomic.tests.wallet.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public class Payment {

    @NonNull
    private final String id;

    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) final String id) {
        this.id = id;
    }
}
