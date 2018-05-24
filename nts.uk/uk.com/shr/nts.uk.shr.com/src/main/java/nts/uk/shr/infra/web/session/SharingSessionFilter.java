package nts.uk.shr.infra.web.session;

import java.io.IOException;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.val;
import nts.uk.shr.com.context.loginuser.SessionLowLayer;

public class SharingSessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		boolean isLoggedIn = CDI.current().select(SessionLowLayer.class).get().isLoggedIn();
		val httpRequest = (HttpServletRequest) request;

		/*
		 * Cookieでセッション情報がやってきて、
		 * 1.かつ、ログインセッション情報が無い場合
		 *   -> 主にcom.webからat.webへの初回の移動とかでありえる
		 * 
		 * 2.かつ、ログインセッション情報があり、それと一致しない場合
		 *   -> 主にcom.webからat.webへの移動前に会社切り替えとかやった場合
		 * 
		 * 上記1,2いずれの場合も、Cookieの情報が正しいとみなして、サーバ側のセッションを書き換える。
		 * ログインしているのにCookieがやってこないケースは無いと思われるが、もしあったとしてもサーバ上のセッション情報でそのまま動作すれば良い
		 */
		if (httpRequest.getCookies() != null) {
			SessionContextCookie.restoreSessionFromCookie(httpRequest, isLoggedIn);
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
