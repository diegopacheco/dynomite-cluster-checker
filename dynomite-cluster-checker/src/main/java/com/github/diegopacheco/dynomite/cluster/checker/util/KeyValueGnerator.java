package com.github.diegopacheco.dynomite.cluster.checker.util;

import java.util.UUID;

/**
 * Generate Ramdon Values for Keys(K) and Values(V).<BR>
 * Generated values are always 33 bytes String. <BR>
 * Sample: <BR>
 *   K9265574155134383a4e865442570f820
 *   V1440ef1a15f2427ba78849aacd89427c
 * 
 * @author diegopacheco
 * @since 27/03/2018
 *
 */
public class KeyValueGnerator {
	
	public static String generateKey() {
		return "K" + UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String generateValue() {
		return "V" + UUID.randomUUID().toString().replaceAll("-", "");
	}

}
