package ksd.common.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WsSessionGroupManager {

	private static Map<String, List<String>> instances;
	private static Map<String, String> sessionToGroup;

	static {
		instances = new HashMap<String, List<String>>();
		sessionToGroup = new HashMap<String, String>();
	}

	private WsSessionGroupManager() {}

	/**
	 * グループに属するWsSessionId一覧を取得する。
	 *
	 * @param groupId
	 * @return
	 */
	public static List<String> getWsSessionIdsByGroupId(String groupId) {
		System.out.println("WsSessionGroupManager#getWsSessionIdsByGroupId() called.");
		return instances.get(groupId);
	}

	/**
	 * WsSessionIdが属するグループのIDを取得する。
	 *
	 * @param wsSessionId
	 * @return
	 */
	public static String getGroupIdByWsSessionId(String wsSessionId) {
		System.out.println("WsSessionGroupManager#getGroupIdByWsSessionId() called.");
		return sessionToGroup.get(wsSessionId);
	}

//	/**
//	 * グループを作成する。
//	 *
//	 * @param groupId
//	 */
//	public static void createGroup(String groupId) {
//		System.out.println("WsSessionGroupManager#createGroup() called.");
//		instances.put(groupId, new ArrayList<String>());
//	}


	/**
	 * グループが存在するか確認する。
	 *
	 * @param groupId
	 * @return
	 */
	public static boolean existGroup(String groupId) {
		System.out.println("WsSessionGroupManager#existGroup() called.");
		return instances.containsKey(groupId);
	}

	/**
	 * グループにWsSessionを追加する。
	 *
	 * @param groupId
	 * @param session
	 */
	public static void setSessionToGroup(String groupId, String wsSessionId) {
		System.out.println("WsSessionGroupManager#setSessionToGroup() called.");
		sessionToGroup.put(wsSessionId, groupId);

		if (!instances.containsKey(groupId)) {
			instances.put(groupId, new ArrayList<String>());
		}
		instances.get(groupId).add(wsSessionId);
	}

	/**
	 * グループからWsSessionを消去する。
	 *
	 * @param wsSession
	 */
	public static void removeSessionFromGroup(String wsSessionId) {
		System.out.println("WsSessionGroupManager#removeSessionFromGroup() called.");
		String groupId = sessionToGroup.get(wsSessionId);
		if (instances.containsKey(groupId)) {
			instances.get(groupId).remove(wsSessionId);
			sessionToGroup.remove(wsSessionId);
		} else {
			System.err.println("すでにグループから外れています。");
		}
	}
}
