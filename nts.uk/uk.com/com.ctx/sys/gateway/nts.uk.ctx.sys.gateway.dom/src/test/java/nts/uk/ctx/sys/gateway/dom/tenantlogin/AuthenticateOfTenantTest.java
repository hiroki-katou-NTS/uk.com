package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.shr.com.net.Ipv4Address;

public class AuthenticateOfTenantTest {
	@Mocked TenantAuthenticate tenantAuthenticate;
	//@Mocked TenantAuthenticateResult tenantAuthenticateResult;
	
	@Injectable
	private AuthenticateOfTenant.Require require;
	
	private static class Dummy{
		static LoginClient loginClient = new LoginClient(Ipv4Address.parse("255.255.255.255"), "");
		static String tenantCode = "000000000000";
		static String password = "0";
		static GeneralDate today = GeneralDate.today();
		
	}

	@Test
	public void authenticate_success() {
		
		new Expectations() {
			{
				require.getTenantAuthentication(Dummy.tenantCode);
				result = Optional.of(tenantAuthenticate);
				
				tenantAuthenticate.verifyPassword(Dummy.password);
				result = true;
				
				tenantAuthenticate.isAvailableAt(Dummy.today);
				result = true;
			}
		};
		
		val result = AuthenticateOfTenant.authenticate(require, Dummy.tenantCode, Dummy.password, Dummy.loginClient);
		assertThat(result.isSuccess()).isTrue();
	}

	@Test
	public void authenticate_fail_Pass() {
		
		new Expectations() {{
			require.getTenantAuthentication(Dummy.tenantCode);
			result = Optional.of(tenantAuthenticate);
			
			tenantAuthenticate.verifyPassword(Dummy.password);
			result = false;
		}};
		
		val result = AuthenticateOfTenant.authenticate(require, Dummy.tenantCode, Dummy.password, Dummy.loginClient);
		
		new Verifications() {{
			require.insert((TenantAuthenticateFailureLog)any);
			times = 0;
		}};
		result.getAtomTask().get().run();
		new Verifications() {{
			require.insert((TenantAuthenticateFailureLog)any);
			times = 1;
		}};
		
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.getErrorMessageID().get()).isEqualTo("Msg_302");
	}

	@Test
	public void authenticate_fail_Expired() {
		
		new Expectations() {{
			require.getTenantAuthentication(Dummy.tenantCode);
			result = Optional.of(tenantAuthenticate);
			
			tenantAuthenticate.verifyPassword(Dummy.password);
			result = true;
			
			tenantAuthenticate.isAvailableAt(Dummy.today);
			result = false;
		}};
		
		val result = AuthenticateOfTenant.authenticate(require, Dummy.tenantCode, Dummy.password, Dummy.loginClient);
		
		new Verifications() {{
			require.insert((TenantAuthenticateFailureLog)any);
			times = 0;
		}};
		result.getAtomTask().get().run();
		new Verifications() {{
			require.insert((TenantAuthenticateFailureLog)any);
			times = 1;
		}};
		
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.getErrorMessageID().get()).isEqualTo("Msg_315");
	}
}
