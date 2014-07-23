package ksd.memo.controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import ksd.common.manager.ManagerOperator;
import ksd.memo.data.Consts;
import ksd.memo.data.Message;

@ServerEndpoint(value = "/messaging/{pageId}/{sessionId}")
public class WsMemo {
	private ManagerOperator mo = new ManagerOperator();

	@OnOpen
	public void onOpen(@PathParam("pageId") String pageId,
			@PathParam("sessionId") String httpSessionId,
			Session wsSession) {

		System.out.println("WsMemo#onOpen called. ");
		mo.wsSessionOpen(httpSessionId, wsSession, pageId);
	}

	@OnClose
	public void onClose(@PathParam("pageId") String pageId,
			@PathParam("sessionId") String sessionId,
			Session session) {
		System.out.println("WsMemo#onClose called.");
		mo.closeWsSesson(session);
	}

	@OnMessage
	public void onMessage(@PathParam("pageId") String pageId,
			@PathParam("sessionId") String sessionId,
			String msg) {

		System.out.print("WsMemo#onMessage called.");
		String[] tmp = msg.split(Consts.separate);
		Message m = new Message(tmp[0]);
		if (tmp[0].equals(Consts.COMMAND_FIXED)) {
			return ;
		} else if (tmp[0].equals(Consts.COMMAND_LOCK)) {
			//m.setTargetId(tmp[1]);
			return ;
		} else if (tmp[0].equals(Consts.COMMAND_ON_EDIT)) {
			m.setTargetId(tmp[1]);
			m.setTxt(tmp[2]);
		} else if (tmp[0].equals(Consts.COMMAND_UNLOCK)) {
			m.setTargetId(tmp[1]);
		}

		mo.pushDataToGroupElseHttpSessionId(m.toJson(), pageId, sessionId);
	}
}