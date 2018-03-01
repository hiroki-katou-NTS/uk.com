package nts.uk.shr.infra.web.request;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;

import lombok.val;

/**
 * Cross-Origin Resource Sharing
 */
public class CorsPreflightFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		val httpRequest = (HttpServletRequest) request;
		
		// webapi "/public" is shared for cross-origin
		if (httpRequest.getPathInfo() != null && httpRequest.getPathInfo().indexOf("/public") == 0) {
			
			val httpResponse =(HttpServletResponse) response;
			httpResponse.addHeader("Access-Control-Allow-Origin", "*");
			httpResponse.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
			httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
			httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			
			// preflight by OPTIONS
			if (httpRequest.getMethod().equals(HttpMethod.OPTIONS)) {
				return;
			}
		}
		
		chain.doFilter(httpRequest, response);
	}

	@Override
	public void destroy() {
		
	}

}
