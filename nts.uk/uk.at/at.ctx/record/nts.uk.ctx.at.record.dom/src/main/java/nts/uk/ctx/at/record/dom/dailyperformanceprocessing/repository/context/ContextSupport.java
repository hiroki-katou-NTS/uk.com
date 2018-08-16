package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * The Class ContextSupport.
 */
public final class ContextSupport {

	/** The Constant threadlocal. */
	protected static final ThreadLocal<Map<Class<?>, Object>> THREAD_LOCAL_MAP = new InheritableThreadLocal<>();

	/**
	 * Gets the or reg deafult provider.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param defaultObj the default obj
	 * @return the or reg deafult provider
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOrRegDeafultProvider(Class<T> clazz, T defaultObj) {
		if (THREAD_LOCAL_MAP.get() == null) {
			THREAD_LOCAL_MAP.set(new HashMap<>());
		}

		if (THREAD_LOCAL_MAP.get().get(clazz) == null) {
			THREAD_LOCAL_MAP.get().put(clazz, defaultObj);
		}

		return (T) THREAD_LOCAL_MAP.get().get(clazz);
	}

	/**
	 * Partition by size.
	 *
	 * @param <T> the generic type
	 * @param list the list
	 * @param size the size
	 * @return the collection
	 */
	public static <T> Collection<List<T>> partitionBySize(Collection<T> list, int size) {
		final AtomicInteger counter = new AtomicInteger(0);
		return list.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size)).values();
	}
}

