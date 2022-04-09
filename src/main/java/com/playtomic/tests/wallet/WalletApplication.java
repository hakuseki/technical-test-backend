package com.playtomic.tests.wallet;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

/**
 * The type Wallet application.
 */
@SpringBootApplication
@CommonsLog
public class WalletApplication {

	/**
	 * Main.
	 *
	 * @param args the args
	 */
	public static void main(final String... args) {
		SpringApplication.run(WalletApplication.class, args);
	}

	/**
	 * Deconstructor.
	 */
	@PreDestroy
	public void deconstructor() {
		log.info("So long and thanks for all the fish!");
	}

}
