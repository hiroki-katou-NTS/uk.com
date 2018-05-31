package nts.uk.ctx.at.record.dom.divergencetime.service;

import java.util.HashMap;
import java.util.Map;

public class DivCheckSharedData {

	private final static Map<String, Object> CONTAINER = new HashMap<>();

	public static void share(String key, Object value) {
		CONTAINER.put(key, value);
	}

	public static boolean isShared(String key) {
		return CONTAINER.containsKey(key);
	}

	public static Object getShared(String key) {
		return CONTAINER.get(key);
	}
	
	public static void clearAll(){
		CONTAINER.clear();
	}
	
	public static void clearShare(String key){
		CONTAINER.remove(key);
	}
}
