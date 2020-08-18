package nts.sample.impls;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.diagnose.stopwatch.embed.EmbedStopwatchScopeFilter;
import nts.arc.layer.ws.preprocess.RequestFilterCollector;
import nts.arc.layer.ws.preprocess.RequestFilterMapping;

@SuppressWarnings("unused")
@Stateless
public class RequestFilterCollectorImpl implements RequestFilterCollector {
	
	private static List<RequestFilterMapping> MAPPING;
	static {
		MAPPING = new ArrayList<>();
		MAPPING.add(RequestFilterMapping.map(".*", new CorsPreflightFilter()));
		MAPPING.add(RequestFilterMapping.map(".*/webapi/.*", new EmbedStopwatchScopeFilter()));
		
//		MAPPING.add(RequestFilterMapping.map(".*", new CsrfProtectionFilter(new PathsToCheckCsrf() {
//			@Override
//			public Set<Pattern> patternsPathNoCheck() {
//				return Stream.of("/test.*")
//						.map(p -> Pattern.compile(p))
//						.collect(Collectors.toSet());
//			}
//		})));
	}

	@Override
	public List<RequestFilterMapping> collect() {
		return MAPPING;
	}

}
