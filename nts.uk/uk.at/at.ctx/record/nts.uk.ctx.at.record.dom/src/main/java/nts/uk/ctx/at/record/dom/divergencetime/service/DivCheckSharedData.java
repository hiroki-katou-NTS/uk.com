package nts.uk.ctx.at.record.dom.divergencetime.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DivCheckSharedData {

	private final static Map<Integer, Container> CONTAINER = new HashMap<>();

	public static void share(String key, Object value) {
		int currentT = currentKey();
		if (!isHaveContainer(currentT)) {
			CONTAINER.put(currentT, Container.open());
		}
		CONTAINER.get(currentT).share(key, value);
	}

	public static boolean isShared(String key) {
		int currentT = currentKey();
		if (isHaveContainer(currentT)) {
			return CONTAINER.get(currentT).isShared(key);
		}
		return false;
	}

	public static Optional<Object> getShared(String key) {
		int currentT = currentKey();
		if (isHaveContainer(currentT)) {
			return Optional.ofNullable(CONTAINER.get(currentT).getShared(key));
		}
		return Optional.empty();
	}

	public static void clearAll() {
		int currentT = currentKey();
		if (isHaveContainer(currentT)) {
			CONTAINER.get(currentT).clearAll();
			CONTAINER.remove(currentT);
		}
	}

	public static void clearShare(String key) {
		int currentT = currentKey();
		if (isHaveContainer(currentT)) {
			CONTAINER.get(currentT).clearShare(key);
		}
	}

	private static boolean isHaveContainer(int currentT) {
		return CONTAINER.containsKey(currentT);
	}

	private static int currentKey() {
		return Thread.currentThread().hashCode();
	}

	private static class Container {
		private final Map<String, Object> DATA_CONTAINER = new HashMap<>();

		public static Container open() {
			return new Container();
		}

		public void share(String key, Object value) {
			DATA_CONTAINER.put(key, value);
		}

		public boolean isShared(String key) {
			return DATA_CONTAINER.containsKey(key);
		}

		public Object getShared(String key) {
			return DATA_CONTAINER.get(key);
		}

		public void clearAll() {
			DATA_CONTAINER.clear();
		}

		public void clearShare(String key) {
			DATA_CONTAINER.remove(key);
		}
	}
}
