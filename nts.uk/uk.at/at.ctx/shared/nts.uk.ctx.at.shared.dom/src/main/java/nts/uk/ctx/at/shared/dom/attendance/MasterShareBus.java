package nts.uk.ctx.at.shared.dom.attendance;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MasterShareBus {

	public static <U> MasterShareContainer<U> open() {
		return new ShareContainer<U>();
	}
	
	public interface MasterShareContainer<U> {
		
		public default void share(U key, Object value) {}

		public default boolean isShared(U key) { return false; }

		public default <T> T getShared(U key) { return null; }

		public default <T> T getShared(U key, Supplier<T> getData) { return null; }

		public default void clearAll() {}

		public default void clearShare(U key) {}
	}

	private static class ShareContainer<U> implements MasterShareContainer<U> {
		
		private Map<U, Object> DATA_CONTAINER;
		
		private ShareContainer() {
			DATA_CONTAINER = new HashMap<>();
		}

		@Override
		public void share(U key, Object value) {
			DATA_CONTAINER.put(key, value);
		}

		@Override
		public boolean isShared(U key) {
			return DATA_CONTAINER.containsKey(key);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> T getShared(U key) {
			Object value = DATA_CONTAINER.get(key);
			return value == null ? null : (T) value;
		}

		@Override
		public <T> T getShared(U key, Supplier<T> getData) {
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
		public void clearShare(U key) {
			DATA_CONTAINER.remove(key);
		}
	}
}
