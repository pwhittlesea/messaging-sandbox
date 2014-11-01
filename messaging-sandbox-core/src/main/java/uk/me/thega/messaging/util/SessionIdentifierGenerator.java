package uk.me.thega.messaging.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class SessionIdentifierGenerator {
	public static String nextSessionId() {
		return new BigInteger(130, random).toString(32).substring(0, 6);
	}

	private final static SecureRandom random = new SecureRandom();
}