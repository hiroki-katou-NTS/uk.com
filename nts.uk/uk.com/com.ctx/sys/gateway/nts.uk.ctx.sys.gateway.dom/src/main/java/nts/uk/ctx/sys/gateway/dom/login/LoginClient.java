package nts.uk.ctx.sys.gateway.dom.login;

import lombok.Value;
import nts.gul.text.StringUtil;
import nts.gul.web.HttpClientIpAddress;
import nts.uk.shr.com.net.Ipv4Address;

import javax.servlet.http.HttpServletRequest;

/**
 * ログインクライアント
 */
@Value
public class LoginClient {
	
	private Ipv4Address ipAddress;
	
	private String userAgent;

	public LoginClient(Ipv4Address ipAddress, String userAgent) {
		this.ipAddress = ipAddress;
		// クソでかい文字列投げられたときにDBエラー起きないようにするため100バイトでカット
		this.userAgent = StringUtil.cutOffAsLengthHalf(userAgent, 100);
	}

	public static LoginClient create(HttpServletRequest request) {
		return new LoginClient(
				Ipv4Address.parse(HttpClientIpAddress.get(request)),
				request.getHeader("user-agent"));
	}
}
