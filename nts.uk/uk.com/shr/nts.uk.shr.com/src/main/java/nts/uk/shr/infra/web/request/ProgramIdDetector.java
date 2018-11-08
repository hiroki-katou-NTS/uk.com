package nts.uk.shr.infra.web.request;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import nts.gul.text.StringUtil;
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
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		httpRequest.setAttribute(InputPart.DEFAULT_CHARSET_PROPERTY, FilterConst.DEFAULT_CHARSET_PROPERTY);
		String target = httpRequest.getHeader(FilterConst.PG_PATH);
		if (target == null) {
			chain.doFilter(httpRequest, response);
			return;
		}

		String ip = FilterHelper.getClientIp(httpRequest);
		String pcName = FilterHelper.getPcName(ip);

		AppContextsConfig.setRequestedWebAPI(new RequestInfo(target, FilterHelper.detectWebapi(target), ip, pcName));
		boolean isStartFromMenu = isStartFromMenu(httpRequest);
		if (!isStartFromMenu) {
			String before = getReferered(httpRequest);
			String webApi = FilterHelper.detectWebapi(before);

			AppContextsConfig.setRequestedWebAPI(new RequestInfo(before, webApi, ip, pcName));
		}
		FilterHelper.detectProgram(target).ifPresent(id -> AppContextsConfig.setProgramId(id));

		chain.doFilter(httpRequest, response);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}
