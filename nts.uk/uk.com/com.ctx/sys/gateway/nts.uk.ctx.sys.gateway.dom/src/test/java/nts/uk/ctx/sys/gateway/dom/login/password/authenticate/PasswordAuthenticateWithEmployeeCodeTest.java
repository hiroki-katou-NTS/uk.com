package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.FailedPasswordAuthenticate.Require;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.LoginPasswordOfUser;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.PasswordState;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.LockOutMessage;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;
import nts.uk.ctx.sys.shared.dom.user.User;

public class PasswordAuthenticateWithEmployeeCodeTest {
	@Mocked IdentifiedEmployeeInfo idenEmpInfo;
	@Mocked AccountLockPolicy accountLockPolicy;
	@Mocked User user;
	@Mocked FailedAuthenticateTask failedAuthenticateTask;
	@Mocked PasswordPolicy passwordPolicy;
	@Mocked PasswordState passStatus;
	@Mocked ValidationResultOnLogin validationResultOnLogin;
	@Mocked LoginPasswordOfUser userPassword;
	
	@Injectable PasswordAuthenticateWithEmployeeCode.Require require;
	
	private static class Dummy {
		private static String tenantCd = "tenten";
		private static String userId = "useruser";
		private static String password = "passpass";
		private static LockOutMessage message = new LockOutMessage("messemesse");
	}
	

	@Test
	//ロックされているかのチェックは面倒だがこいつを使う。
	//private methodとして切り出したが、NtsassertでBusinessExceptionやられるとInvokeExceptionに変化しており
	//BussinessExceptionとして捕まえることができなかった。(やりようはあるかもしれない)
	public void accountLocked() {
		
		new Expectations() {{
			idenEmpInfo.getTenantCode();
			result = Dummy.tenantCd;
			
			idenEmpInfo.getUserId();
			result = Dummy.userId;
			
			require.getAccountLockPolicy(Dummy.tenantCd);
			result = Optional.of(accountLockPolicy);
			
			accountLockPolicy.isLocked(require, Dummy.userId);
			result = true;
			
			accountLockPolicy.getLockOutMessage();
			result = Dummy.message;
		}};
		
		new MockUp<FailedPasswordAuthenticate>() {
			@Mock
			public FailedAuthenticateTask failed(Require require, IdentifiedEmployeeInfo identifiedEmployee, String password) {
				return failedAuthenticateTask;
			}
		};
		
		assertThatThrownBy(() -> PasswordAuthenticateWithEmployeeCode.authenticate(require, idenEmpInfo, Dummy.password))
		.isInstanceOfSatisfying(BusinessException.class, e -> {
			assertThat(e.getMessage()).isEqualTo(Dummy.message.v());
		});
	}

	@Test
	public void fail_authenticate() {
		
		new Expectations() {{
			idenEmpInfo.getTenantCode();
			result = Dummy.tenantCd;
			
			idenEmpInfo.getUserId();
			result = Dummy.userId;
			
			require.getAccountLockPolicy(Dummy.tenantCd);
			result = Optional.of(accountLockPolicy);
			
			accountLockPolicy.isLocked(require, Dummy.userId);
			result = false;
			
			require.getLoginPasswordOfUser(Dummy.userId);
			result = Optional.of(userPassword);
			
			userPassword.matches(Dummy.password);
			result = false;
		}};
		
		val result = PasswordAuthenticateWithEmployeeCode.authenticate(require, idenEmpInfo, Dummy.password);
		assertThat(result.isFailure()).isTrue();
	}
	
	@Test
	public void success_authenticate() {
		
		new Expectations() {{
			idenEmpInfo.getTenantCode();
			result = Dummy.tenantCd;
			
			idenEmpInfo.getUserId();
			result = Dummy.userId;
			
			require.getAccountLockPolicy(Dummy.tenantCd);
			result = Optional.of(accountLockPolicy);
			
			accountLockPolicy.isLocked(require, Dummy.userId);
			result = false;
			
			require.getLoginPasswordOfUser(Dummy.userId);
			result = Optional.of(userPassword);

			userPassword.matches(Dummy.password);
			result = true;
			
			require.getPasswordPolicy(Dummy.tenantCd);
			result = passwordPolicy;
		}};
		
		val result = PasswordAuthenticateWithEmployeeCode.authenticate(require, idenEmpInfo, Dummy.password);
		assertThat(result.isSuccess()).isTrue();
	}
	
}