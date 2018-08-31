package nts.uk.shr.infra.web.session;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.val;
import nts.gul.text.StringUtil;
import nts.uk.shr.com.context.AppContextsConfig;
import nts.uk.shr.com.context.RequestInfo;
import nts.uk.shr.com.program.nosession.PathsNoSession;
import nts.uk.shr.infra.web.ScreenPath;
import nts.uk.shr.infra.web.util.FilterHelper;

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
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestPagePath = (httpRequest).getRequestURL().append(getQueryString(httpRequest)).toString();
		
		String ip = FilterHelper.getClientIp((HttpServletRequest) request);
		String pcName = FilterHelper.getPcName(ip);
		
		AppContextsConfig.setRequestedWebAPI(new RequestInfo(
													requestPagePath, 
													FilterHelper.detectWebapi(requestPagePath), 
													ip, pcName));
		
		FilterHelper.detectProgram(requestPagePath).ifPresent(id -> AppContextsConfig.setProgramId(id));
		
		chain.doFilter(request, response);
	}

	private String getQueryString(HttpServletRequest httpRequest) {
		return StringUtil.isNullOrEmpty(httpRequest.getQueryString(), true) ? "" : "?" + httpRequest.getQueryString();
	}

	@Override
	public void destroy() {
	}

}
