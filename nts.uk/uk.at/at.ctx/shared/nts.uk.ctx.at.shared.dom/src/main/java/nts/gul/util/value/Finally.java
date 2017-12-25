package nts.gul.util.value;

import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * The value class present that value will be initialized finally. 
 *
 * @param <V> type of value
 */
@AllArgsConstructor
public class Finally<V> {

	private V value;
	
	private boolean hasInitialized;
	
	public static <V> Finally<V> of(@NonNull V value) {
		return new Finally<>(value, true);
	}
	
	public static <V> Finally<V> empty() {
		return new Finally<>(null, false);
	}
	
	public void set(@NonNull V value) {
		if (this.hasInitialized) {
			throw new RuntimeException("This value has been initialized by: " + this.value);
		}
		
		this.value = value;
		this.hasInitialized = true;
	}
	
	public V get() {
		if (!this.hasInitialized) {
			throw new RuntimeException("This value has not been initialized");
		}
		
		return this.value;
	}
	
	/**
	 * 値を保持しているか判定する
	 * @return　保持している
	 */
	public boolean isPresent() {
		return hasInitialized;
	}
}
