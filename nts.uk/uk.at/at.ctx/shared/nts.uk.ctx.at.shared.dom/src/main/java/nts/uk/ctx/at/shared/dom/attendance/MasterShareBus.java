package nts.uk.ctx.at.shared.dom.attendance;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MasterShareBus {

	public static MasterShareContainer open() {
		return new ShareContainer();
	}
	
	public interface MasterShareContainer {
		
		public default void share(String key, Object value) {}

		public default boolean isShared(String key) { return false; }

		public default <T> T getShared(String key) { return null; }

		public default <T> T getShared(String key, Supplier<T> getData) { return null; }

		public default void clearAll() {}

		public default void clearShare(String key) {}
	}

	private static class ShareContainer implements MasterShareContainer {
		
		private Map<String, Object> DATA_CONTAINER;
		
		private ShareContainer() {
			DATA_CONTAINER = new HashMap<>();
		}

		@Override
		public void share(String key, Object value) {
			DATA_CONTAINER.put(key, value);
		}

		@Override
		public boolean isShared(String key) {
			return DATA_CONTAINER.containsKey(key);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> T getShared(String key) {
			Object value = DATA_CONTAINER.get(key);
			return value == null ? null : (T) value;
		}

		@Override
		public <T> T getShared(String key, Supplier<T> getData) {
			if(isShared(key)){
				return getShared(key);
			}
			T val = getData.get();
			share(key, val);
			return val;
		}
		
		@Override
		public void clearAll() {
			DATA_CONTAINER.clear();
		}

		@Override
		public void clearShare(String key) {
			DATA_CONTAINER.remove(key);
		}
	}
}
