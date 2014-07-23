package ksd.common.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Locker {
	private static Map<String, String> locker;
	private static Set<String> httpSessionIds;

	static {
		locker = new HashMap<String, String>();
		httpSessionIds = new HashSet<String>();
	}

	public static boolean lock(String targetId, String httpSessionId) //throws SecurityException
	{
		System.out.println("Locker#lock() called.");
		if (!httpSessionIds.contains(httpSessionId)) {
			if (!locker.containsKey(targetId)) {
				httpSessionIds.add(httpSessionId);
				locker.put(targetId, httpSessionId);
				return true;
			} else {
				// 競合
				return false;
			}
		} else {
			//refresh(targetId, httpSessionId);
			//throw new SecurityException("1 user multi lock");
			System.err.println("1 user multi lock");
			return false;
		}
	}

	public static void unlock(String target, String httpSessionId)
	throws SecurityException {
		System.out.println("Locker#unlock() called.");
		if (locker.containsKey(target)) {

			if (httpSessionId.equals(locker.get(target))) {

				if (httpSessionIds.contains(httpSessionId)) {
					httpSessionIds.remove(httpSessionId);
					locker.remove(target);
				} else {
					System.err.println("ロックしていないのに解放しようとした。");
				}
			} else {
				System.err.println("他人のロックを解放しようとした。");
				refresh(target, httpSessionId);
				throw new SecurityException("unlock error.");
			}
		} else {
			System.err.println("ロックされていないものを解放しようとした。");
			refresh(target, httpSessionId);
			// throw new SecurityException("unlock error.");
		}
	}

	private static void refresh(String target, String httpSessionId) {
		if (locker.containsKey(target)) {
			locker.remove(target);
		}
		if (httpSessionIds.contains(httpSessionId)) {
			httpSessionIds.remove(httpSessionId);
		}
	}
}
