package nts.uk.shr.infra.application.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import waffle.servlet.NegotiateSecurityFilter;

/**
 * SSO security filter.
 *
 */
public class SSOSecurityFilter extends NegotiateSecurityFilter {
	
	/**
	 * Sign on query string
	 */
	private static final String SIGN_ON = "signon=on"; 

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String queryString = httpRequest.getQueryString();
		if (queryString != null && queryString.matches(SIGN_ON)) {
			super.doFilter(request, response, chain);
		}
		chain.doFilter(request, response);
	}
	
}
