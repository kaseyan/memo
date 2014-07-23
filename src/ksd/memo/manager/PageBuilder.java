package ksd.memo.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ksd.memo.data.Page;

public class PageBuilder {

	private static Map<String, Page> instances;

	static {
		instances = new HashMap<String, Page>();
	}

	private PageBuilder() {}

	public static Page getPage(String name) {
		if (!instances.containsKey(name))
			instances.put(name, new Page());
		return instances.get(name);
	}

	public static Set<String> getNames() {
		return instances.keySet();
	}
}
