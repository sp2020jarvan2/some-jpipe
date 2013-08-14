package org.swordess.jpipe.util;

import java.util.List;

public class StringUtils {

	private StringUtils() {
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static String emptyStr(int length) {
		StringBuilder empty = new StringBuilder();
		for (int i = 0; i < length; i++) {
			empty.append(' ');
		}
		return empty.toString();
	}
	
	public static String replaceAll(String string, int[] offsets, String replacement) {
		StringBuilder builder = new StringBuilder(string);
		for (int offset : offsets) {
			builder.replace(offset, offset + 1, replacement);
		}
		return builder.toString();
	}
	
	public static String replaceAll(String string, List<Integer> offsets, String replacement) {
		StringBuilder builder = new StringBuilder(string);
		for (int offset : offsets) {
			builder.replace(offset, offset + 1, replacement);
		}
		return builder.toString();
	}
	
}
