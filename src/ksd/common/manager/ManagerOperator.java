package ksd.common.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

public class ManagerOperator {

	public ManagerOperator() {}

	/**
	 * HttpSessionが開始されたときの処理。
	 *
	 * @param session
	 */
	public void httpSessionCreated(HttpSession session) {
		HttpSessionManager.setHttpSession(session);
	}

	/**
	 * HttpSessionが終了するときの処理。
	 *
	 * @param httpSessionId
	 */
	public void closeHttpSession(String httpSessionId) {
		String wsSessionId = SessionMapper.getWsSessionId(httpSessionId);
		WsSessionManager.deleteSession(wsSessionId);
		SessionMapper.deleteHttp(httpSessionId);
		HttpSessionManager.invalidate(httpSessionId);
	}

	/**
	 * WsSessionがOpenしたときの処理。
	 *
	 * @param httpSessionId
	 * @param wsSession
	 * @param groupId
	 */
	public void wsSessionOpen(String httpSessionId, Session wsSession, String groupId) {
		WsSessionManager.setSession(wsSession);
		WsSessionGroupManager.setSessionToGroup(groupId, wsSession.getId());
		SessionMapper.add(httpSessionId, wsSession.getId());
	}

	/**
	 * WsSessionがCloseしたときの処理。
	 *
	 * @param wsSessoin
	 */
	public void closeWsSesson(Session wsSession) {
		WsSessionManager.deleteSession(wsSession.getId());
		WsSessionGroupManager.removeSessionFromGroup(wsSession.getId());
		SessionMapper.deleteWs(wsSession.getId());
	}

	/**
	 * あるグループに属するWsSession全て(ただし指定したHttpSessionは除く。nullの場合は全WsSession)にデータをPushする。
	 *
	 * @param groupId
	 * @param httpSessionId
	 * @param data
	 */
	public void pushDataToGroupElseHttpSessionId(String data, String groupId, String elseHttpSessionId) {
		System.out.println("ManagerOperator#pushDataToGroupElseHttpSessionId() called." + data);

		if (!WsSessionGroupManager.existGroup(groupId)) {
			System.err.println("No such groupId.:"+groupId);
		}

		List<String> wsSessionIds = WsSessionGroupManager.getWsSessionIdsByGroupId(groupId);
		String elseWsSessionId = null;
		if (elseHttpSessionId != null) {
			elseWsSessionId = SessionMapper.getWsSessionId(elseHttpSessionId);
		}
		pushDataToGroupElseWsSessionId(wsSessionIds, data, elseWsSessionId);
//		try {
//			for (String wsSessionId : wsSessionIds) {
//				Session wsSession = WsSessionManager.getSession(wsSessionId);
//				if (!wsSession.getId().equals(elseWsSessionId)) {
//					wsSession.getBasicRemote().sendText(data);
//				}
//			}
//		} catch (IOException e) {
//			System.err.println("ManagerOperator#pushDataToGroupElseHttpSessionId() failed." + e.getMessage());
//		}
	}

	private void pushDataToGroupElseWsSessionId(List<String> wsSessionIds, String data, String elseWsSessionId) {
		System.out.println("ManagerOperator#pushDataToGroupElseWsSessionId() called." + data);
		if (elseWsSessionId != null) {
			wsSessionIds.remove(elseWsSessionId);
		}
		try {
			//int i=0;
			for (String wsSessionId : wsSessionIds) {
				//i++;
				WsSessionManager.getSession(wsSessionId).getBasicRemote().sendText(data);
			}
			//System.out.println(i);
		} catch (IOException e) {
			System.err.println("ManagerOperator#pushDataToGroupElseHttpSessionId() failed." + e.getMessage());
		} finally {
			if (elseWsSessionId != null) {
				wsSessionIds.add(elseWsSessionId);
			}
		}
	}

}
