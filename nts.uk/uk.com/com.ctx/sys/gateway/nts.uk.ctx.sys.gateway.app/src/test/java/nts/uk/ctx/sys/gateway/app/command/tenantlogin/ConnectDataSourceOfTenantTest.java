package nts.uk.ctx.sys.gateway.app.command.tenantlogin;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Verifications;
import nts.arc.task.tran.AtomTask;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.AuthenticateTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationFailureLog;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationResult;
import nts.uk.shr.com.net.Ipv4Address;
import nts.uk.shr.com.system.property.UKServerSystemProperties;
import nts.uk.shr.infra.data.TenantLocatorService;

public class ConnectDataSourceOfTenantTest {
	@Injectable
	private TenantAuthenticationResult tenantAuthenticateResult;
	
	@Injectable
	private AuthenticateTenant.Require require;
	
	private static class Dummy{
		static LoginClient loginClient = new LoginClient(Ipv4Address.parse("255.255.255.255"), "");
		static String tenantCode = "000000000000";
		static String password = "0";
		static AtomTask atomTask = AtomTask.of(AtomTask.none());
	}
	
	@Test
	public void notUseTL() {
		
		MutableValue<Boolean> isCalledConnect = new MutableValue<>(false);
		MutableValue<Boolean> isCalledDisconnect = new MutableValue<>(false);
		
		new MockUp<UKServerSystemProperties>() {
			@Mock
			public boolean usesTenantLocator() {
				return false;
			}
		};
		assertThat(isCalledConnect.get()).isFalse();
		new MockUp<TenantLocatorService>() {
			@Mock
			public void connect(String tenantCode) {
				isCalledConnect.set(true);
			}
			@Mock
			public void disconnect() {
				isCalledDisconnect.set(true);
			}
		};
		
		new MockUp<AuthenticateTenant>() {
			@Mock
			public TenantAuthenticationResult authenticate(AuthenticateTenant.Require require, String tenantCode, String password, LoginClient loginClient) {
				return TenantAuthenticationResult.success();
			}
		};
		
		ConnectDataSourceOfTenant.connect(require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);
		
		assertThat(isCalledConnect.get()).isFalse();
		assertThat(isCalledDisconnect.get()).isFalse();
	}
	
	@Test
	public void useTL_fail_Connect() {
		
		MutableValue<Boolean> isCalledConnect = new MutableValue<>(false);
		MutableValue<Boolean> isCalledDisconnect = new MutableValue<>(false);
		
		new MockUp<UKServerSystemProperties>() {
			@Mock
			public boolean usesTenantLocator() {
				return true;
			}
		};
		assertThat(isCalledConnect.get()).isFalse();
		new MockUp<TenantLocatorService>() {
			@Mock
			public void connect(String tenantCode) {
				isCalledConnect.set(true);
			}
			@Mock
			public boolean isConnected() {
				return false;
			}
			@Mock
			public void disconnect() {
				isCalledDisconnect.set(true);
			}
		};
		
		val result = ConnectDataSourceOfTenant.connect(require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);

		new Verifications() {{
			require.insert((TenantAuthenticationFailureLog)any);
			times = 0;
		}};
		result.getAtomTask().get().run();
		new Verifications() {{
			require.insert((TenantAuthenticationFailureLog)any);
			times = 1;
		}};
		
		assertThat(isCalledConnect.get()).isTrue();
		assertThat(isCalledDisconnect.get()).isFalse();
	}
	
	@Test
	public void useTL_fail() {
		
		MutableValue<Boolean> isCalledConnect = new MutableValue<>(false);
		MutableValue<Boolean> isCalledDisconnect = new MutableValue<>(false);
		
		new MockUp<UKServerSystemProperties>() {
			@Mock
			public boolean usesTenantLocator() {
				return true;
			}
		};
		assertThat(isCalledConnect.get()).isFalse();
		new MockUp<TenantLocatorService>() {
			@Mock
			public void connect(String tenantCode) {
				isCalledConnect.set(true);
			}
			@Mock
			public boolean isConnected() {
				return true;
			}
			@Mock
			public void disconnect() {
				isCalledDisconnect.set(true);
			}
		};
		
		new MockUp<AuthenticateTenant>() {
			@Mock
			public TenantAuthenticationResult authenticate(AuthenticateTenant.Require require, String tenantCode, String password, LoginClient loginClient) {
				return TenantAuthenticationResult.failedToAuthPassword(Dummy.atomTask);
			}
		};
		
		val result = ConnectDataSourceOfTenant.connect(require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);
		
		assertThat(isCalledConnect.get()).isTrue();
		assertThat(isCalledDisconnect.get()).isTrue();
		assertThat(result.getErrorMessageID().isPresent()).isTrue();
	}
	
	@Test
	public void useTL_success() {
		
		MutableValue<Boolean> isCalledConnect = new MutableValue<>(false);
		MutableValue<Boolean> isCalledDisconnect = new MutableValue<>(false);
		
		new MockUp<UKServerSystemProperties>() {
			@Mock
			public boolean usesTenantLocator() {
				return true;
			}
		};
		assertThat(isCalledConnect.get()).isFalse();
		new MockUp<TenantLocatorService>() {
			@Mock
			public void connect(String tenantCode) {
				isCalledConnect.set(true);
			}
			@Mock
			public boolean isConnected() {
				return true;
			}
			@Mock
			public void disconnect() {
				isCalledDisconnect.set(true);
			}
		};
		
		new MockUp<AuthenticateTenant>() {
			@Mock
			public TenantAuthenticationResult authenticate(AuthenticateTenant.Require require, String tenantCode, String password, LoginClient loginClient) {
				return TenantAuthenticationResult.success();
			}
		};
		
		val result = ConnectDataSourceOfTenant.connect(require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);
		
		assertThat(isCalledConnect.get()).isTrue();
		assertThat(isCalledDisconnect.get()).isFalse();
		assertThat(result.getErrorMessageID().isPresent()).isFalse();
	}
}
