package com.playtomic.tests.wallet.model;
//tag::TopupRequest[]

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * The class WalletTopupRequest
 *
 * @author maw, (c) Compliance Solutions Strategies, 2022-04-10
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletTopupRequest {
    /**
     * The Card number.
     */
    private String cardNumber;
    /**
     * The Amount.
     */
    private BigDecimal amount;
    /**
     * The Wallet id.
     */
    private String walletId;
}
//end::TopupRequest[]
