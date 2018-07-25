package nts.uk.shr.infra.web.request;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import nts.uk.shr.infra.web.util.FilterConst;

public class FirstFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		NtsHttpServletRequest ntsRequest = NtsHttpServletRequest.wrap((HttpServletRequest) request);
		if(ntsRequest.containsParam(FilterConst.MENU_FLAG)){
			ntsRequest.setAttribute(FilterConst.JUMP_FROM_MENU, true);
			ntsRequest.removeParam(FilterConst.MENU_FLAG);
		}
		chain.doFilter(ntsRequest, response);
	}

	@Override
	public void destroy() {
	}

}
