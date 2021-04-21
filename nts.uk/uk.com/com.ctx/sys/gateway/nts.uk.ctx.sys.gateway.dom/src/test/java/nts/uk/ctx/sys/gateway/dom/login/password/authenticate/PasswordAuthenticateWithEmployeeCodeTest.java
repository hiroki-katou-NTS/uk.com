package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.FailedPasswordAuthenticate.Require;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy.ValidateOnLoginRequire;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin.Status;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class PasswordAuthenticateWithEmployeeCodeTest {

	@Injectable
	private PasswordAuthenticateWithEmployeeCode.Require passwordAuthRequire;
	
	@Injectable
	private PasswordPolicy.ValidateOnLoginRequire  passwordPolicyRequire;
	
	@Test
	public void fail_authenticate() {
		
		new MockUp<FailedPasswordAuthenticate>() {
			@Mock
			public FailedAuthenticateTask failed(Require require, IdentifiedEmployeeInfo identifiedEmployee, String password) {
				return PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY.FAILED_TASKS;
			}
		};
		new MockUp<User>() {
			@Mock
			public boolean isCorrectPassword(String password) {
				return false;
			}
		};
		
		val result = PasswordAuthenticateWithEmployeeCode.authenticate(
				passwordAuthRequire, 
				PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY.EMP_INFO, 
				PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY.PASSWORD);
		assertThat(result.isFailed()).isTrue();
	}

	@Test
	public void success_authenticate() {
		
		
		new MockUp<FailedPasswordAuthenticate>() {
			@Mock
			public FailedAuthenticateTask failed(Require require, IdentifiedEmployeeInfo identifiedEmployee, String password) {
				return PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY.FAILED_TASKS;
			}
		};
		
		new MockUp<User>() {
			@Mock
			public boolean isCorrectPassword(String password) {
				return true;
			}
		};
		
		new MockUp<PasswordPolicy>(){
			@Mock
			public ValidationResultOnLogin validateOnLogin(ValidateOnLoginRequire require,
					String userId,
					String password,
					PassStatus passwordStatus) {
				return new ValidationResultOnLogin(Status.INITIAL, null, null);
			}
		};
		
		new Expectations() {{
			passwordAuthRequire.getPasswordPolicy(PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY.CONTRACT_CODE);
			result = Optional.of(PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY.PASSWORD_POLICY);
		}};
		
		val result = PasswordAuthenticateWithEmployeeCode.authenticate(
				passwordAuthRequire, 
				PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY.EMP_INFO,
				PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY.PASSWORD);
		assertThat(result.isSuccess()).isTrue().as("処理が正常終了した");
	}
	
}
