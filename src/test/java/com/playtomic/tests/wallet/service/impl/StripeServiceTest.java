package com.playtomic.tests.wallet.service.impl;


import com.playtomic.tests.wallet.service.Payment;
import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * This test is failing with the current implementation.
 * <p>
 * How would you test this?
 */
@ExtendWith(MockitoExtension.class)
public class StripeServiceTest {

    /**
     * The Test uri.
     */
    URI testUri = URI.create("http://how-would-you-test-me.localhost");
    /**
     * The S.
     */
    @Mock
    StripeService s = new StripeService(testUri, testUri, new RestTemplateBuilder());

    /**
     * Test exception.
     */
    @Test
    public void test_exception() {
        final Payment payment = new Payment(UUID.randomUUID()
                                                .toString());
        payment.setAmount(new BigDecimal("5"));
        when(s.charge(anyString(), any())).thenThrow(new StripeAmountTooSmallException());

        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            s.charge("4242 4242 4242 4242", new BigDecimal(5));
        });

        verify(s, times(1)).charge(anyString(), any());
        verifyNoMoreInteractions(s);
    }

    /**
     * Test ok.
     *
     * @throws StripeServiceException the stripe service exception
     */
    @Test
    public void test_ok() throws StripeServiceException {
        final Payment payment = new Payment(UUID.randomUUID()
                                                .toString());
        payment.setAmount(new BigDecimal("15"));
        when(s.charge(anyString(), any())).thenReturn(payment);
        final Payment charge = s.charge("4242 4242 4242 4242", new BigDecimal(15));

        assertEquals(payment.getAmount(), new BigDecimal("15"));

        verify(s, times(1)).charge(anyString(), any());
        verifyNoMoreInteractions(s);

    }
}
