package org.swordess.jpipe.util;

import java.util.Collection;

public class CollectionUtils {

	private CollectionUtils() {
	}
	
	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.isEmpty();
	}
	
	public static boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}
	
}
