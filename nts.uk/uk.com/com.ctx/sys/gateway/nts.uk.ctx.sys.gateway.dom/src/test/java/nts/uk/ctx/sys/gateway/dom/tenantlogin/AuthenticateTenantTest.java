package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.shr.com.net.Ipv4Address;

public class AuthenticateTenantTest {
	@Mocked TenantAuthentication tenantAuthenticate;
	
	@Injectable
	private AuthenticateTenant.Require require;
	
	private static class Dummy{
		static final LoginClient LOGIN_CLIENT = new LoginClient(Ipv4Address.parse("255.255.255.255"), "");
		static final String TENANT_CODE = "000000000000";
		static final String PASSWORD = "0";
		static final GeneralDate TODAY = GeneralDate.today();
		
	}

	@Test
	public void authenticate_success() {
		
		new Expectations() {
			{
				require.getTenantAuthentication(Dummy.TENANT_CODE);
				result = Optional.of(tenantAuthenticate);
				
				tenantAuthenticate.verifyPassword(Dummy.PASSWORD);
				result = true;
				
				tenantAuthenticate.isAvailableAt(Dummy.TODAY);
				result = true;
			}
		};
		
		val result = AuthenticateTenant.authenticate(require, Dummy.TENANT_CODE, Dummy.PASSWORD, Dummy.LOGIN_CLIENT);
		assertThat(result.isSuccess()).isTrue();
	}

	@Test
	public void authenticate_fail_Pass() {
		
		new Expectations() {{
			require.getTenantAuthentication(Dummy.TENANT_CODE);
			result = Optional.of(tenantAuthenticate);
			
			tenantAuthenticate.verifyPassword(Dummy.PASSWORD);
			result = false;
		}};
		
		val result = AuthenticateTenant.authenticate(require, Dummy.TENANT_CODE, Dummy.PASSWORD, Dummy.LOGIN_CLIENT);
		
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.getErrorMessageID().get()).isEqualTo("Msg_302");
		
		NtsAssert.atomTask(
				() -> AuthenticateTenant.authenticate(require, Dummy.TENANT_CODE, Dummy.PASSWORD, Dummy.LOGIN_CLIENT).getAtomTask().get(), 
				any ->require.insert(any.get()));
	}

	@Test
	public void authenticate_fail_Expired() {
		
		new Expectations() {{
			require.getTenantAuthentication(Dummy.TENANT_CODE);
			result = Optional.of(tenantAuthenticate);
			
			tenantAuthenticate.verifyPassword(Dummy.PASSWORD);
			result = true;
			
			tenantAuthenticate.isAvailableAt(Dummy.TODAY);
			result = false;
		}};
		
		val result = AuthenticateTenant.authenticate(require, Dummy.TENANT_CODE, Dummy.PASSWORD, Dummy.LOGIN_CLIENT);
		
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.getErrorMessageID().get()).isEqualTo("Msg_315");
		
		NtsAssert.atomTask(
				() -> AuthenticateTenant.authenticate(require, Dummy.TENANT_CODE, Dummy.PASSWORD, Dummy.LOGIN_CLIENT).getAtomTask().get(), 
				any ->require.insert(any.get()));
	}
}
