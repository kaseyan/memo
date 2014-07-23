package ksd.common.manager;

import java.util.HashMap;
import java.util.Map;

public class SessionMapper {
	private static Map<String, String> wsToHttp;
	private static Map<String, String> httpToWs;

	static {
		wsToHttp = new HashMap<String, String>();
		httpToWs = new HashMap<String, String>();
	}

	private SessionMapper() {}

	public static void add(String httpSessionId, String wsSessionId) {
		wsToHttp.put(wsSessionId, httpSessionId);
		httpToWs.put(httpSessionId, wsSessionId);
	}

	/**
	 * wsSessionIdからhttpSessionIdを取得する。
	 *
	 * @param wsSessionId
	 * @return
	 */
	public static String getHttpSessionId(String wsSessionId) {
		return wsToHttp.get(wsSessionId);
	}

	/**
	 * httpSessionIdからwsSessionIdを取得する。
	 *
	 * @param httpSessionId
	 * @return
	 */
	public static String getWsSessionId(String httpSessionId) {
		return httpToWs.get(httpToWs.get(httpSessionId));
	}

	/**
	 * wsSessoinIdに関する情報を消去する。
	 *
	 * @param wsSessionId
	 */
	public static void deleteWs(String wsSessionId) {
		String httpSessionId = wsToHttp.get(wsSessionId);
		wsToHttp.remove(wsSessionId);
		httpToWs.remove(httpSessionId);
	}

	/**
	 * httpSessionに関する情報を消去する。
	 *
	 * @param httpSessionId
	 */
	public static void deleteHttp(String httpSessionId) {
		String wsSessionId = httpToWs.get(httpSessionId);
		wsToHttp.remove(wsSessionId);
		httpToWs.remove(httpSessionId);
	}

}
