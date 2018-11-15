package nts.uk.shr.infra.web.request;

import java.io.IOException;
import java.util.stream.Stream;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import nts.gul.text.StringUtil;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.com.program.ProgramsManager;
import nts.uk.shr.infra.web.util.FilterConst;
import nts.uk.shr.infra.web.util.StartPageLogService;

public class StartPageLogWriter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		writeLog(request);

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	public void writeLog(ServletRequest request) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestPagePath = httpRequest.getRequestURL().toString();

		boolean isStartFromMenu = isStartFromMenu(httpRequest);
		
		ScreenIdentifier target = ScreenIdentifier.create(requestPagePath);
		
		if(ProgramsManager.KAF000B.getPId().equals(target.getProgramId() + target.getScreenId())){
			return;
		}

		StartPageLogService logService = CDI.current().select(StartPageLogService.class).get();

		logService.writeLog(isStartFromMenu ? null : getReferered(httpRequest), target);
	}

	private boolean isStartFromMenu(HttpServletRequest httpRequest) {
		if (httpRequest.getCookies() == null) {
			return false;
		}
		return Stream.of(httpRequest.getCookies()).filter(c -> c.getName().equals(FilterConst.JUMP_FROM_MENU))
				.findFirst().isPresent();
	}

	private String getReferered(HttpServletRequest r) {
		String refereredPath = r.getHeader(FilterConst.REFERED_REQUEST);

		if (StringUtil.isNullOrEmpty(refereredPath, true)) {
			return null;
		}

		return refereredPath;
	}

}
