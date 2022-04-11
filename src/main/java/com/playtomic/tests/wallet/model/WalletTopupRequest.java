package com.playtomic.tests.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

//tag::WalletTopupRequest[]
/**
 * The class WalletTopupRequest
 *
 * @author maw, 2022-04-10
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
//end::WalletTopupRequest[]
