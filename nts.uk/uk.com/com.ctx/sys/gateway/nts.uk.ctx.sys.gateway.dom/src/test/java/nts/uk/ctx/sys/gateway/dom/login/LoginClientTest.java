package nts.uk.ctx.sys.gateway.dom.login;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.uk.shr.com.net.Ipv4Address;

public class LoginClientTest {
	
	private static class Dummy {
		final static Ipv4Address ipAddress = Ipv4Address.parse("255.255.255.255"); 
	}
	
	@Test
	public void stringCut() {
		val longUserAgent 	= "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "xxxxx";
		
		val result = new LoginClient(Dummy.ipAddress, longUserAgent);
		
		assertThat(result.getUserAgent().length() <= 100).isTrue();
	}
}
