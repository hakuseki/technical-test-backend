package com.playtomic.tests.wallet.model;


import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
    /**
     * The Id.
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    long id;

    /**
     * The Wallet id.
     */
    @Column(name = "wallet_id")
    @JsonProperty
    String walletId;

    /**
     * The Balance.
     */
    @Column
    @JsonProperty
    BigDecimal balance;

    /**
     * The Created.
     */
    @Transient
    @JsonProperty
    Timestamp created;

    /**
     * The Updated.
     */
    @Transient
    @JsonProperty
    Timestamp updated;
}
//end::Wallet[]
