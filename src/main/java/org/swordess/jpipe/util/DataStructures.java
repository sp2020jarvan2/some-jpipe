package org.swordess.jpipe.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataStructures {

	private DataStructures() {
	}
	
	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<E>();
	}
	
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}
	
	public static <K, V> Map.Entry<K, V> newSimpleEntry(K key, V value) {
		return new AbstractMap.SimpleEntry<K, V>(key, value);
	}
	
}
