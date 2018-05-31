package nts.uk.shr.infra.web.request;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.ws.preprocess.RequestFilterCollector;
import nts.arc.layer.ws.preprocess.RequestFilterMapping;
import nts.arc.layer.ws.preprocess.filters.RequestPerformanceLogFilter;
import nts.arc.security.csrf.CsrfProtectionFilter;
import nts.uk.shr.com.program.nosession.PathsNoSession;
import nts.uk.shr.infra.application.auth.WindowsAccountCatcher;
import nts.uk.shr.infra.web.session.ScreenLoginSessionValidator;
import nts.uk.shr.infra.web.session.SharingSessionFilter;
import nts.uk.shr.infra.web.session.WebApiLoginSessionValidator;

@Stateless
public class UkRequestFilterCollector implements RequestFilterCollector {
	
	private static class PathPattern {
		static final String ALL_REQUESTS = ".*";
		static final String ALL_SCREENS = ".*\\.xhtml.*";
		static final String ALL_WEB_APIS = ".*/webapi/.*";
		static final String LOGIN_SCREENS = ".*/view/ccg/007/.*";
	}
	
	private static final List<RequestFilterMapping> FILTERS = Arrays.asList(
			RequestFilterMapping.map(PathPattern.ALL_REQUESTS, new RequestPerformanceLogFilter()),
			RequestFilterMapping.map(PathPattern.ALL_REQUESTS, new CorsPreflightFilter()),
			RequestFilterMapping.map(PathPattern.ALL_REQUESTS, new SharingSessionFilter()),
			RequestFilterMapping.map(PathPattern.ALL_WEB_APIS, new ProgramIdDetector()),
			RequestFilterMapping.map(PathPattern.ALL_SCREENS, new ScreenLoginSessionValidator()),
			RequestFilterMapping.map(PathPattern.ALL_WEB_APIS, new WebApiLoginSessionValidator()),
			RequestFilterMapping.map(PathPattern.LOGIN_SCREENS, new WindowsAccountCatcher())
//			RequestFilterMapping.map(PathPattern.ALL_WEB_APIS, new CsrfProtectionFilter(PathsNoSession.WEB_APIS))
			);

	@Override
	public List<RequestFilterMapping> collect() {
		return FILTERS;
	}

}
