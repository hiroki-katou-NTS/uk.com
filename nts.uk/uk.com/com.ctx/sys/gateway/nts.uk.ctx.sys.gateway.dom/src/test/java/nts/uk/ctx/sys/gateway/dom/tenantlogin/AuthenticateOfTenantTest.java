package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.shr.com.net.Ipv4Address;

public class AuthenticateOfTenantTest {
	@Mocked TenantAuthenticate tenantAuthenticate;
	
	@Injectable
	private AuthenticateOfTenant.Require require;
	
	private static class Dummy{
		static LoginClient loginClient = new LoginClient(Ipv4Address.parse("255.255.255.255"), "");
		static String tenantCode = "000000000000";
		static String password = "0";
		static DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2000, 4, 1), GeneralDate.ymd(3000, 4, 1));
		static TenantAuthenticate tenantAuth = new TenantAuthenticate(tenantCode, password, datePeriod);
	}

	@Test
	public void authenticateTest_success() {
		
		new Expectations() {
			{
				require.getTenantAuthentication(Dummy.tenantCode);
				result = Optional.of(Dummy.tenantAuth);
				
				tenantAuthenticate.authentication(Dummy.password);
				result = true;
			}
		};
		
		val result = AuthenticateOfTenant.authenticate(require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);
		assertThat(result.isSuccess()).isTrue();
	}

	@Test
	public void authenticateTest_fail() {
		
		new Expectations() {
			{
				require.getTenantAuthentication(Dummy.tenantCode);
				result = Optional.of(Dummy.tenantAuth);
				
				tenantAuthenticate.authentication(Dummy.password);
				result = false;
			}
		};
		
		val result = AuthenticateOfTenant.authenticate(require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);
		assertThat(result.isSuccess()).isFalse();
	}
}
