package com.playtomic.tests.wallet.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//tag::ResponseType[]
/**
 * The class ResponseType
 *
 * @author maw, (c) Compliance Solutions Strategies, 2022-04-09
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseType {
    private String message;
}
//end::ResponseType[]
