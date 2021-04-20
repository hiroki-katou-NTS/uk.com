package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.FailedPasswordAuthenticate.Require;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy.ValidateOnLoginRequire;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyTestHelper;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin.Status;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class PasswordAuthenticateWithEmployeeCodeTest {

	@Injectable
	private PasswordAuthenticateWithEmployeeCode.Require passwordAuthRequire;
	
	@Injectable
	private PasswordPolicy.ValidateOnLoginRequire  passwordPolicyRequire;
	
	@Test
	public void fail_authenticate() {
		EmployeeDataMngInfoImport imp = PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY_IMPORTED;
		User user = PasswordAuthenticateWithEmployeeCodeTestHelper.USER;
		
		IdentifiedEmployeeInfo empInfo = new IdentifiedEmployeeInfo(imp, user);
		
		new MockUp<FailedPasswordAuthenticate>() {
			@Mock
			public FailedAuthenticateTask failed(Require require, IdentifiedEmployeeInfo identifiedEmployee, String password) {
				return PasswordAuthenticateWithEmployeeCodeTestHelper.anyTask;
			}
		};
		val result = PasswordAuthenticateWithEmployeeCode.authenticate(passwordAuthRequire, empInfo, "");
		assertThat(result.isFailed()).isTrue();
	}

	@Test
	public void success_authenticate() {
		EmployeeDataMngInfoImport imp = PasswordAuthenticateWithEmployeeCodeTestHelper.DUMMY_IMPORTED;
		User user = PasswordAuthenticateWithEmployeeCodeTestHelper.USER;
		
		IdentifiedEmployeeInfo empInfo = new IdentifiedEmployeeInfo(imp, user);
		
		
		new MockUp<FailedPasswordAuthenticate>() {
			@Mock
			public FailedAuthenticateTask failed(Require require, IdentifiedEmployeeInfo identifiedEmployee, String password) {
				return PasswordAuthenticateWithEmployeeCodeTestHelper.anyTask;
			}
		};
		
		new MockUp<User>() {
			@Mock
			public boolean isCorrectPassword(String password) {
				return true;
			}
		};
		
		MutableValue<Boolean> isCalled = new MutableValue<Boolean>();
		new MockUp<PasswordPolicy>(){
			@Mock
			public ValidationResultOnLogin validateOnLogin(ValidateOnLoginRequire require,
					String userId,
					String password,
					PassStatus passwordStatus) {
				isCalled.set(true);
				return new ValidationResultOnLogin(Status.INITIAL, null, null);
			}
		};
		
		val passAuthResult = Optional.of(PasswordPolicyTestHelper.dummyPolicy);
		new Expectations() {{
			passwordAuthRequire.getPasswordPolicy("contractCode");
			result = passAuthResult;
		}};
		
		val result = PasswordAuthenticateWithEmployeeCode.authenticate(passwordAuthRequire, empInfo, DUMMY.PASSWORD);
		assertThat(result.isSuccess()).isTrue().as("処理が正常終了した");
		assertThat(isCalled.get()).isTrue().as("パスワードチェック処理を読んだ。");
	}
	
}
