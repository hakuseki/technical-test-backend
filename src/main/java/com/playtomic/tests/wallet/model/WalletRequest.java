package com.playtomic.tests.wallet.model;
//tag::WalletRequest[]

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The class WalletRequest
 *
 * @author maw, (c) Compliance Solutions Strategies, 2022-04-10
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WalletRequest {
    /**
     * The Wallet id.
     */
    private String walletId;
}
//end::WalletRequest[]
