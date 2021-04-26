package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.shr.com.net.Ipv4Address;

public class TenantAuthenticateFailureLogTest {
	
	private static class Dummy{
		final static GeneralDateTime dateTime = GeneralDateTime.now(); 
		final static LoginClient loginClient = new LoginClient(Ipv4Address.parse("255.255.255.255"), "agentagentagent");
	}
	
	@Test
	public void stringCut() {
		val longTenantCode 	= "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "xxxxx";
		
		val longPassword 	= "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "12345678901234567890"
							+ "xxxxx";
		
		val result = new TenantAuthenticateFailureLog(Dummy.dateTime, Dummy.loginClient, longTenantCode, longPassword);
		
		assertThat(result.getTriedTenantCode().length() <= 100).isTrue();
		assertThat(result.getTriedPassword().length() <= 100).isTrue();
	}
}
