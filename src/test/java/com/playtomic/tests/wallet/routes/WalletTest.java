package com.playtomic.tests.wallet.routes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.model.Wallet;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The type Wallet test.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WalletTest {

    /**
     * The constant headers.
     */
    private static HttpHeaders headers;
    /**
     * The Object mapper.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * The Server port.
     */
    @LocalServerPort
    int serverPort;
    /**
     * The Rest template.
     */
    @Autowired
    TestRestTemplate restTemplate;

    /**
     * Sets up.
     */
    @BeforeAll
    static void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Should display a valid wallet.
     *
     * @throws JSONException           the json exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    @DisplayName("Should display a valid Wallet")
    void shouldDisplayAValidWallet() throws JSONException, JsonProcessingException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("walletId", "516beebc-6c01-4794-af53-08b4793c5ed7");

        final HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);

        final ResponseEntity<String> response = restTemplate.
                postForEntity("http://localhost:" + serverPort + "/wallet", request, String.class);

        final Wallet wallet = objectMapper.readValue(response.getBody(),
                                                     new TypeReference<>() {
                                                     });

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(wallet.getWalletId(), "516beebc-6c01-4794-af53-08b4793c5ed7");
    }

    /**
     * Should return an empty message if no wallet exists.
     *
     * @param walletId the wallet id
     * @throws JSONException the json exception
     */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"516beebc-6c01-4794-af53-08b4793c5ed8", "whatever"})
    @DisplayName("Should return an empty message if no wallet exists")
    void shouldReturnAnEmptyMessageIfNoWalletExists(final String walletId) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("walletId", walletId);

        final HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);

        final ResponseEntity<String> response = restTemplate.
                postForEntity("http://localhost:" + serverPort + "/wallet", request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }


    /**
     * Should topup a wallet successfully.
     *
     * @throws JSONException           the json exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    @DisplayName("Should topup a wallet successfully")
    void shouldTopupAWalletSuccessfully() throws JSONException, JsonProcessingException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("walletId", "516beebc-6c01-4794-af53-08b4793c5ed7");
        jsonObject.put("amount", 123.45);
        jsonObject.put("cardNumber", "1234567890");

        final HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);

        final ResponseEntity<String> response = restTemplate.
                postForEntity("http://localhost:" + serverPort + "/wallet/topup", request, String.class);

        final Wallet wallet = objectMapper.readValue(response.getBody(),
                                                     new TypeReference<>() {
                                                     });

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(wallet.getWalletId(), "516beebc-6c01-4794-af53-08b4793c5ed7");
        assertEquals(0,
                     wallet.getBalance()
                           .compareTo(new BigDecimal("246.9")));

    }

    /**
     * Should do nothing if an invalid wallet id is present.
     *
     * @param walletId the wallet id
     * @throws JSONException the json exception
     */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"516beebc-6c01-4794-af53-08b4793c5ed8", "whatever"})
    @DisplayName("Should do nothing if an invalid walletId is present")
    void shouldDoNothingIfAnInvalidWalletIdIsPresent(final String walletId) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("walletId", walletId);
        jsonObject.put("amount", 123.45);
        jsonObject.put("cardNumber", "1234567890");

        final HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);

        final ResponseEntity<String> response = restTemplate.
                postForEntity("http://localhost:" + serverPort + "/wallet/topup", request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }


}
