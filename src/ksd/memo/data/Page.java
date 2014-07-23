package ksd.memo.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
	private String id;
	private String name;

	/**
	 * 前Lineを取得するためのMap
	 */
	private Map<String, Line> lineMap;

	/**
	 *
	 */
	private List<Line> list;

	public Page() {
		System.out.println("Page() called.");
		list = new ArrayList<Line>();
		lineMap = new HashMap<String, Line>();

		Line root = new Line("Enter");
		list.add(root);
		lineMap.put(root.hashCode()+"", root);
	}

	/**
	 * 前Lineの次にLineを挿入する
	 *
	 * @param prevCode 前LineのHashCode
	 * @param line 挿入するLine
	 */
	public void add(String prevCode, Line line) {
		// 前Line取得
		Line prevLine = lineMap.get(prevCode);
		// 前Lineの次に挿入
		list.add(list.indexOf(prevLine)+1, line);
		lineMap.put(line.hashCode()+"", line);
	}

	public void set(String key, String value) {
		lineMap.get(key).setData(value);
	}

	public List<Line> getAll() {
		return this.list;
	}
}
