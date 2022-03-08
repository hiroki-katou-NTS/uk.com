package nts.uk.screen.at.app.query.kdp.kdp003.a;

import javax.ejb.Stateless;

import nts.gul.web.HttpClientIpAddress;
import nts.uk.shr.com.net.Ipv4Address;

@Stateless
public class GetIPRequest {
	public GetIPRequestDto getIP(AuthenticateTenantInput param) {

		return new GetIPRequestDto(HttpClientIpAddress.get(param.reques), Ipv4Address.parse(HttpClientIpAddress.get(param.reques))) ;
	}
}
