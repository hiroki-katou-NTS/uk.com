package nts.uk.shr.infra.web.request;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import nts.uk.shr.com.context.AppContextsConfig;
import nts.uk.shr.com.context.RequestInfo;
import nts.uk.shr.infra.web.util.FilterConst;
import nts.uk.shr.infra.web.util.FilterHelper;

/**
 * RequestPreProcessor
 * 
 */
public class ProgramIdDetector implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter #doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setAttribute(InputPart.DEFAULT_CHARSET_PROPERTY, FilterConst.DEFAULT_CHARSET_PROPERTY);
		String requestPagePath = ((HttpServletRequest) request).getHeader(FilterConst.PG_PATH);
		if (requestPagePath == null) {
			chain.doFilter(request, response);
			return;
		}
		
		String ip = FilterHelper.getClientIp((HttpServletRequest) request);
		String pcName = FilterHelper.getPcName(ip);
		
		AppContextsConfig.setRequestedWebAPI(new RequestInfo(
													requestPagePath, 
													FilterHelper.detectWebapi(requestPagePath), 
													ip, pcName));
		
		FilterHelper.detectProgram(requestPagePath).ifPresent(id -> AppContextsConfig.setProgramId(id));

		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}
