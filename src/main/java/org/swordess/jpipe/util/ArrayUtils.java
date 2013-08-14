package org.swordess.jpipe.util;

public class ArrayUtils {

	private ArrayUtils() {
	}
	
	public static boolean isEmpty(Object[] array) {
		return null == array || array.length == 0;
	}
	
	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}
	
}
