package nts.uk.ctx.sys.gateway.dom.login;

import lombok.Value;
import nts.uk.shr.com.net.Ipv4Address;

@Value
public class LoginClient {
	
	private Ipv4Address ipAddress;
	
	private String userAgent;

}
