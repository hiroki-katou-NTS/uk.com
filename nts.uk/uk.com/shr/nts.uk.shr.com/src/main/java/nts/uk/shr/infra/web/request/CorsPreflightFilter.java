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
		} else if (httpRequest.getHeader("origin") != null && (httpRequest.getHeader("origin").contains("localhost:3000") || httpRequest.getHeader("origin").contains("0.0.0.0:3000"))) {
			
			/** TODO: this code just for DEV mode, when deploy mobile in same wildfly with other web pack, need to remove this */
			val httpResponse =(HttpServletResponse) response;
			
			if(httpRequest.getHeader("origin").contains("localhost:3000")) {
				httpResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
			} else {
				httpResponse.addHeader("Access-Control-Allow-Origin", "http://0.0.0.0:3000");				
			}
			
			httpResponse.addHeader("Access-Control-Allow-Headers", "X-Requested-With, origin, content-type, accept, authorization, PG-Path, X-CSRF-TOKEN, MOBILE");
			httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
			httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			if (httpRequest.getMethod().equals(HttpMethod.OPTIONS)) {
				httpResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
				return;
			}
		}
		
		chain.doFilter(httpRequest, response);
	}

	@Override
	public void destroy() {
		
	}

}
