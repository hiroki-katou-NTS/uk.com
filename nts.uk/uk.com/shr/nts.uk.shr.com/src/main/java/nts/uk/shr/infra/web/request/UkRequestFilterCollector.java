package nts.uk.shr.infra.web.request;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.ws.preprocess.RequestFilterCollector;
import nts.arc.layer.ws.preprocess.RequestFilterMapping;
import nts.arc.layer.ws.preprocess.filters.RequestPerformanceLogFilter;
import nts.uk.shr.infra.web.session.ScreenLoginSessionValidator;

@Stateless
public class UkRequestFilterCollector implements RequestFilterCollector {

	private static final List<RequestFilterMapping> FILTERS = Arrays.asList(
			RequestFilterMapping.map(".*", new RequestPerformanceLogFilter()),
			RequestFilterMapping.map(".*/webapi/.*", new ProgramIdDetector()),
			RequestFilterMapping.map(".*\\.xhtml.*", new ScreenLoginSessionValidator())
			);

	@Override
	public List<RequestFilterMapping> collect() {
		return FILTERS;
	}

}
