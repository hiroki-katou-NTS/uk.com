package nts.uk.shr.infra.web.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import lombok.val;
import nts.uk.shr.com.program.nosession.PathsNoSession;
import nts.uk.shr.infra.web.ScreenPath;

public class ScreenLoginSessionValidator implements Filter {
	
	private static final ServletPathsNoSession PATHS_NO_SESSION = new ServletPathsNoSession(
			PathsNoSession.WEB_SCREENS,
			r -> r.getServletPath());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (!PATHS_NO_SESSION.validate(request)) {
            val httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(ScreenPath.basedOn(request).error().sessionTimeout());
            return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
