package nts.uk.ctx.at.shared.dom.attendance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class MasterShareBus {

	public static <U> MasterShareContainer<U> open() {
		return new ShareContainer<U>();
	}
	
	public interface MasterShareContainer<U> {
		
		public default void share(U key, Object value) {}

		public default boolean isShared(U key) { return false; }

		public <T> T getShared(U key);

		public <T> T getShared(U key, Supplier<T> getData);

		public default void clearAll() {}

		public default void clearShare(U key) {}
	}

	private static class ShareContainer<U> implements MasterShareContainer<U> {
		
		private Map<U, Object> DATA_CONTAINER;
		
		private ShareContainer() {
			System.out.println("CONSTRUCT ShareContainer");
			DATA_CONTAINER = new ConcurrentHashMap<>();
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
			if (value == null) {
				System.out.println("DATA_CONAINTER return null: " + key);
				System.out.println(DATA_CONTAINER.toString());
			}
			return value == null ? null : (T) value;
		}

		@Override
		public <T> T getShared(U key, Supplier<T> getData) {
			synchronized(DATA_CONTAINER) {
				if(isShared(key)){
					return getShared(key);
				}
				T val = getData.get();
				share(key, val);
				return val;
			}
		}
		
		@Override
		public void clearAll() {
			System.out.println("MasterShareBus.clearAll");
			DATA_CONTAINER.clear();
		}

		@Override
		public void clearShare(U key) {
			System.out.println("MasterShareBus.clearShare: " + key);
			DATA_CONTAINER.remove(key);
		}
	}
}
