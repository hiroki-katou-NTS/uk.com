package nts.uk.screen.at.app.query.kdp.kdp003.a;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import nts.gul.web.HttpClientIpAddress;

@Stateless
public class GetIPAddress {

	public IPAddressDto get(HttpServletRequest request) {

		return new IPAddressDto(HttpClientIpAddress.get(request));

	}

}
