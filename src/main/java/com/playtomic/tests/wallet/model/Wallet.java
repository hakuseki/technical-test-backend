package com.playtomic.tests.wallet.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

//tag::Wallet[]
/**
 * The class Wallet
 *
 * @author maw, (c) Compliance Solutions Strategies, 2022-04-09
 * @version 1.0
 */
@Entity(name = "wallet")
@Table(name = "wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Wallet implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "wallet_id")
    String walletId;

    @Column
    BigDecimal balance;

//    @Column(columnDefinition = "TIMESTAMP")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    LocalDateTime created;
//
//    @Column(columnDefinition = "TIMESTAMP")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    LocalDateTime updated;

}
//end::Wallet[]
