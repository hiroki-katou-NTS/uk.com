package nts.uk.ctx.sys.gateway.app.command.tenantlogin;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.arc.task.tran.AtomTask;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.AuthenticateOfTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateResult;
import nts.uk.shr.com.net.Ipv4Address;
import nts.uk.shr.com.system.property.UKServerSystemProperties;
import nts.uk.shr.infra.data.TenantLocatorService;

public class ConnectDataSourceOfTenantTest {
	
	MutableValue<Boolean> isCalledConnect = new MutableValue<>(false);
	MutableValue<Boolean> isCalledDisconnect = new MutableValue<>(false);
	
	private static class Dummy{
		static LoginClient loginClient = new LoginClient(Ipv4Address.parse("255.255.255.255"), "");
		static String tenantCode = "000000000000";
		static String password = "0";
		static AtomTask atomTask = AtomTask.of(AtomTask.none());
		
		@Injectable
		static AuthenticateOfTenant.Require require;
	}
	
	@Test
	public void connectTest_notUseTL() {
		
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
		
		new MockUp<AuthenticateOfTenant>() {
			@Mock
			public TenantAuthenticateResult authenticate(AuthenticateOfTenant.Require require, LoginClient loginClient, String tenantCode, String password) {
				return TenantAuthenticateResult.success();
			}
		};
		
		ConnectDataSourceOfTenant.connect(Dummy.require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);
		
		assertThat(isCalledConnect.get()).isFalse();
		assertThat(isCalledDisconnect.get()).isFalse();
	}
	
	@Test
	public void connectTest_useTL_success() {
		
		new MockUp<UKServerSystemProperties>() {
			@Mock
			public boolean usesTenantLocator() {
				return true;
			}
		};
		assertThat(isCalledConnect.get()).isTrue();
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
		
		new MockUp<AuthenticateOfTenant>() {
			@Mock
			public TenantAuthenticateResult authenticate(AuthenticateOfTenant.Require require, LoginClient loginClient, String tenantCode, String password) {
				return TenantAuthenticateResult.success();
			}
		};
		
		ConnectDataSourceOfTenant.connect(Dummy.require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);
		
		assertThat(isCalledConnect.get()).isTrue();
		assertThat(isCalledDisconnect.get()).isFalse();
	}
	
	@Test
	public void connectTest_useTL_fail() {
		
		new MockUp<UKServerSystemProperties>() {
			@Mock
			public boolean usesTenantLocator() {
				return true;
			}
		};
		assertThat(isCalledConnect.get()).isTrue();
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
		
		new MockUp<AuthenticateOfTenant>() {
			@Mock
			public TenantAuthenticateResult authenticate(AuthenticateOfTenant.Require require, LoginClient loginClient, String tenantCode, String password) {
				return TenantAuthenticateResult.failed(Dummy.atomTask);
			}
		};
		
		ConnectDataSourceOfTenant.connect(Dummy.require, Dummy.loginClient, Dummy.tenantCode, Dummy.password);
		
		assertThat(isCalledConnect.get()).isTrue();
		assertThat(isCalledDisconnect.get()).isTrue();
	}
}
