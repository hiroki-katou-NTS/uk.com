package nts.uk.shr.infra.web.request;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
import nts.uk.shr.com.program.ProgramsManager;
import nts.uk.shr.com.program.WebAppId;

/**
 * RequestPreProcessor
 * 
 */
public class ProgramIdDetector implements Filter {
	
	/**
	 * Program path header.
	 */
	private static final String PG_PATH = "PG-Path";
	
	/**
	 * Web app map.
	 */
	@SuppressWarnings("serial")
	private final Map<WebAppId, String> webApps = new HashMap<WebAppId, String>(){
		{
			put(WebAppId.COM, "nts.uk.com.web");
			put(WebAppId.PR, "nts.uk.pr.web");
			put(WebAppId.AT, "nts.uk.at.web");
		}
	};

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter
	 * #doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setAttribute(InputPart.DEFAULT_CHARSET_PROPERTY,"ISO-8859-1");
		String requestPagePath = ((HttpServletRequest) request).getHeader(PG_PATH);
		if (requestPagePath == null) {
			chain.doFilter(request, response);
			return;
		}
		Iterator<Entry<WebAppId, String>> iterator = webApps.entrySet().iterator();
		Entry<WebAppId, String> entry = null;
		int startIndex = -1;
		int endIndex = requestPagePath.lastIndexOf("?");
		String partialPath = null;
		WebAppId appId = null;
		
		while (iterator.hasNext()) {
			entry = iterator.next();
			startIndex = requestPagePath.indexOf(entry.getValue());
			if (startIndex != -1) {
				 startIndex += entry.getValue().length();
				 partialPath = endIndex == -1 ? requestPagePath.substring(startIndex)
					 						: requestPagePath.substring(startIndex, endIndex);
				 appId = entry.getKey();
				 break;
			}
		}
		ProgramsManager.idOf(appId, partialPath).ifPresent(id -> AppContextsConfig.setProgramId(id));
		
		String ip = getClientIp((HttpServletRequest) request);
		String pcName = getPcName(ip);
		
		AppContextsConfig.setRequestedWebAPI(new RequestInfo(requestPagePath, entry.getValue(), ip, pcName));
		
		chain.doFilter(request, response);
	}
	
	/** get request ip address */
	private static String getClientIp(HttpServletRequest request) {
        if (request != null) {
        	String remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (StringUtil.isNullOrEmpty(remoteAddr, true)) {
                return request.getRemoteAddr();
            }
        }

        return "";
    }
	
	/** get request pc name */
	private static String getPcName(String ip) {
		if(StringUtil.isNullOrEmpty(ip, true)){
			return "";
		}
		try {
			if(ip.equals(InetAddress.getLoopbackAddress().getHostAddress())){
				return InetAddress.getLocalHost().getHostName();
			}
            InetAddress host = InetAddress.getByName(ip);
            return host.getHostName();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
		return "";
    }

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}
