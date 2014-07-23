package ksd.common.data;

import java.util.HashMap;
import java.util.Map;

public class Message {
	public Map<String, String> dataMap;

	public Message() {
		dataMap = new HashMap<String, String>();
	}

	public void put(String key, String value) {
		dataMap.put(key, value);
	}

	public String get(String key) {
		return dataMap.get(key);
	}

	public String toJson() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (Map.Entry<String, String> e : dataMap.entrySet()) {
			sb.append(e.getKey() + ":\"" + e.getValue() + "\",");
		}
		//System.out.println(sb.toString());
		String res = sb.substring(0, sb.lastIndexOf(",")) + "}";
		System.out.println(res);
		return res;
	}
}
