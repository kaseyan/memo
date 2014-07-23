package ksd.common.manager;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

public class WsSessionManager {
	private static Map<String, Session> instances;
	static {
		instances = new HashMap<String,Session>();
	}

	private WsSessionManager() {}

	public static void setSession(Session session) {
		instances.put(session.getId(),session);
	}

	public static Session getSession(String sessionId) {
		return instances.get(sessionId);
	}

	public static void deleteSession(String sessionId) {
		instances.remove(sessionId);
	}

}
