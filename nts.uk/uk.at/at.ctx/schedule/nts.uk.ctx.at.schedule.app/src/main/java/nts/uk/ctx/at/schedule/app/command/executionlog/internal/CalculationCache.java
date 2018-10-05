package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import nts.uk.ctx.at.schedule.dom.adapter.ScTimeImport;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeParam;

public class CalculationCache {

	private static final ThreadLocal<CalculationCache> threadCache = new ThreadLocal<>();
	
	private Map<ScTimeParam.ForCache, ScTimeImport> scTimeCache = new HashMap<>();
	
	public static void initialize() {
		threadCache.set(new CalculationCache());
	}
	
	public static void clear() {
		threadCache.set(null);
	}
	
	private CalculationCache() {
	}
	
	public static ScTimeImport getResult(ScTimeParam.ForCache paramKey, Supplier<ScTimeImport> resultGenerator) {
		
		CalculationCache cache = threadCache.get();
		if (cache == null) {
			return resultGenerator.get();
		}
		
		ScTimeImport cached = cache.scTimeCache.get(paramKey);
		if (cached != null) {
			return cached;
		}
		
		ScTimeImport generated = resultGenerator.get();
		cache.scTimeCache.put(paramKey, generated);
		return generated;
	}
	
}
