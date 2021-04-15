package nts.uk.ctx.sys.gateway.dom.login;

import lombok.Value;
import nts.uk.shr.com.net.Ipv4Address;

@Value
public class LoginClient {
	
	private Ipv4Address ipAddress;
	
	private String userAgent;

	public LoginClient(Ipv4Address ipAddress, String userAgent) {
		this.ipAddress = ipAddress;
		// 長すぎるとめんどいのでカット
		this.userAgent = userAgent.substring(0, 100);
	}
}
