package ksd.memo.manager;

import ksd.common.manager.Locker;
import ksd.common.manager.ManagerOperator;
import ksd.memo.data.Consts;
import ksd.memo.data.Line;
import ksd.memo.data.Message;
import ksd.memo.data.MessageArray;
import ksd.memo.data.Page;

public class ApplicationOperator {

	private static ManagerOperator mo;

	static {
		mo = new ManagerOperator();
	}

	public static Message lockLine(String pageId, String lineId, String httpSessionId) {
		System.out.println("ApplicationOperator#lockLine() called.");
		Message m = new Message(Consts.COMMAND_LOCK);
		m.setTargetId(lineId);
		if (Locker.lock(lineId, httpSessionId)) {
			m.setTxt(Consts.COMMAND_SUCCESS);
		} else {
			m.setTxt(Consts.COMMAND_FAIL);
		}

		mo.pushDataToGroupElseHttpSessionId(m.toJson(), pageId, httpSessionId);

		return m;
	}

	public static Message unlockLine(String pageId, String lineId, String httpSessionId) {
		System.out.println("ApplicationOperator#unlockLine() called.");
		Message m = new Message(Consts.COMMAND_UNLOCK);
		m.setTargetId(lineId);
		Locker.unlock(lineId, httpSessionId);

		mo.pushDataToGroupElseHttpSessionId(m.toJson(), pageId, httpSessionId);

		return m;
	}

	public static Message fix(String pageId, String lineId, String data, String httpSessionId) {
		System.out.println("ApplicationOperator#fix() called.:"+data);

		Page page = PageBuilder.getPage(pageId);
		String[] datas = data.split("。");
		MessageArray ma = new MessageArray();

		// 1つ目
		Message base = new Message(Consts.COMMAND_FIXED);
		base.setTargetId(lineId);
		base.setTxt(datas[0]);
		ma.add(base);
		page.set(lineId, datas[0]);

		// 2つ目以降
		String before = lineId;
		for (int i=1; i<datas.length; i++) {
			String d = datas[i];
			Line l = new Line(d);

			Message m = new Message(Consts.COMMAND_FIXED);
			m.setTargetId(l.hashCode()+"");
			m.setTxt(d);
			ma.add(m);

			page.add(before, l);
			before = l.hashCode()+"";
		}

		Message res = new Message(Consts.COMMAND_FIXED);
		res.setTargetId(lineId);
		res.setTxt(ma.toJson());
		//mo.pushDataToGroupElseHttpSessionId(res.toJson(), pageId, httpSessionId);
		mo.pushDataToGroupElseHttpSessionId(res.toJson(), pageId, null);
		return res;
	}
}
