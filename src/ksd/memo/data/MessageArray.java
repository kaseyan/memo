package ksd.memo.data;

import java.util.ArrayList;
import java.util.List;

public class MessageArray {
	public List<Message> datas;

	public MessageArray() {
		datas = new ArrayList<Message>();
	}

	public void add(Message m) {
		datas.add(m);
	}

	public String toJson() {
		String res = null;
		String temp = "";

		if (datas.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (Message data : datas) {
				sb.append(data.toJson()+",");
			}
			temp = sb.substring(0, sb.lastIndexOf(",")-1) + "}";
			temp = temp.replaceAll("\"", "'");
		}
		System.out.println(res);
		return "messages:[" + temp + "]";
	}
}
