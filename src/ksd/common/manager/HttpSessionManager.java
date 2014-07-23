package ksd.common.manager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class HttpSessionManager {
	private static Map<String, HttpSession> instances;
	static {
		instances = new HashMap<String, HttpSession>();
	}

	private HttpSessionManager() {}

	public static HttpSession getHttpSession(String sessionId) {
		return instances.get(sessionId);
	}

	public static boolean contain(String sessionId) {
		return instances.containsKey(sessionId);
	}

	public static void setHttpSession(HttpSession session) {
		if (!contain(session.getId())) {
			instances.put(session.getId(), session);
		} else {

		}
	}

	public static void invalidate(String sessionId) {
		HttpSession target = instances.get(sessionId);
		if (target != null) {
			target.invalidate();
		}
		instances.remove(sessionId);
	}

}
