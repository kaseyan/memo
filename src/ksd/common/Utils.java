package ksd.common;

import java.util.UUID;

public class Utils {

	public static String getUniqId() {
		return UUID.randomUUID().toString();
	}
}
