package com.playtomic.tests.wallet.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Wallet controller.
 */
@RestController
public class WalletController {
    /**
     * The Log.
     */
    private final Logger log = LoggerFactory.getLogger(WalletController.class);

    /**
     * Log.
     */
//    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }
}
