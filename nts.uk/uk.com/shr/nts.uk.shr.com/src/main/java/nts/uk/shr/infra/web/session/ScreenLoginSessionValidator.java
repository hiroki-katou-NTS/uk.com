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
		
		String requestPagePath = ((HttpServletRequest) request).getRequestURL().toString();
		
		String ip = getClientIp((HttpServletRequest) request);
		String pcName = getPcName(ip);
		
		AppContextsConfig.setRequestedWebAPI(new RequestInfo(
													requestPagePath, 
													FilterHelper.detectWebapi(requestPagePath), 
													ip, pcName));
		
		FilterHelper.detectProgram(requestPagePath).ifPresent(id -> AppContextsConfig.setProgramId(id));
		
		chain.doFilter(request, response);
	}

	/** get request ip address */
	private static String getClientIp(HttpServletRequest request) {
		if (request != null) {
			String remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (StringUtil.isNullOrEmpty(remoteAddr, true)) {
				remoteAddr = request.getRemoteAddr();
			}
			if(remoteAddr.equals(InetAddress.getLoopbackAddress().getHostAddress())){
				try {
					remoteAddr = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
			return remoteAddr;
		}

		return "";
	}

	/** get request pc name */
	private static String getPcName(String ip) {
		if (StringUtil.isNullOrEmpty(ip, true)) {
			return "";
		}
		try {
			if (ip.equals(InetAddress.getLoopbackAddress().getHostAddress())) {
				return InetAddress.getLocalHost().getHostName();
			}
			InetAddress host = InetAddress.getByName(ip);
			return host.getHostName();
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
		return "";
	}

	@Override
	public void destroy() {
	}

}
