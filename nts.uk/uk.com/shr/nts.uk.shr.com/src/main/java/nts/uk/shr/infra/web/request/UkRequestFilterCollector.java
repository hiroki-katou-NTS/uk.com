package nts.uk.shr.infra.web.request;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.servlet.Filter;

import nts.arc.layer.ws.preprocess.RequestFilterCollector;
import nts.arc.layer.ws.preprocess.filters.RequestPerformanceLogFilter;

@Stateless
public class UkRequestFilterCollector implements RequestFilterCollector {

	private static final List<Filter> FILTERS = Arrays.asList(
			new RequestPerformanceLogFilter(),
			new ProgramIdDetector()
			);
	
	@Override
	public List<Filter> collect() {
		return FILTERS;
	}

}
