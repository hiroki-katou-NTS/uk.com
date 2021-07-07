package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.LoginPasswordOfUser;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.PasswordChangeLogDetail;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy.ValidateOnLoginRequire;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity.PasswordComplexityRequirement;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class PasswordPolicyTest {

	@Injectable
	private ValidateOnLoginRequire require;
	
	@Test
	public void calcRemainingDays() {
		int passwordChangeLastDays = -5;
		int rangeDays = 2;
		
		List<PasswordChangeLogDetail> list = Arrays.asList(
				new PasswordChangeLogDetail(GeneralDateTime.now().addDays(passwordChangeLastDays), PasswordPolicyTestHelper.DUMMY.PASSWORD));
		
		new Expectations() {{
			require.getPasswordChangeLog(PasswordPolicyTestHelper.DUMMY.USER_ID);
			result = new LoginPasswordOfUser(PasswordPolicyTestHelper.DUMMY.USER_ID, list);;
		}};
		
		val result = (int)NtsAssert.Invoke.privateMethod(
				PasswordPolicyTestHelper.setValidityPeriod(rangeDays), 
				"calculateRemainingDays", 
				require,
				PasswordPolicyTestHelper.DUMMY.USER_ID);
		assertThat(result).isEqualTo(passwordChangeLastDays + rangeDays);
	}
	
	@Test
	public void resetPassword() {
		val dummyInstance = PasswordPolicyTestHelper.DUMMY.PASSWORD_POLICY;
		ValidationResultOnLogin result = run(dummyInstance, PassStatus.Reset);
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.RESET);
	}
	
	@Test
	public void initialPass() {
		val dummyInstance = PasswordPolicyTestHelper.DUMMY.PASSWORD_POLICY;
		ValidationResultOnLogin result = run(dummyInstance, PassStatus.InitPassword);
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.INITIAL);
	}

	@Test
	public void passComplex() {
		
		new MockUp<PasswordComplexityRequirement>() {
			@Mock
			public List<String> validatePassword(String password){
				return PasswordPolicyTestHelper.DUMMY.STRING_LIST;
			}
		};
		val dummyInstance = PasswordPolicyTestHelper.DUMMY.PASSWORD_POLICY;
		ValidationResultOnLogin result = run(dummyInstance, PassStatus.Official);
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.VIOLATED);
	}
	
	@Test
	public void use_Policy() {
		val dummyInstance = PasswordPolicyTestHelper.DUMMY.PASSWORD_POLICY;
		ValidationResultOnLogin result = run(dummyInstance, PassStatus.Official);
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.OK);
	}
	
	private ValidationResultOnLogin run(PasswordPolicy dummyInstance, PassStatus passStatus) {
		return (ValidationResultOnLogin)dummyInstance.violatedOnLogin(
				require, 
				PasswordPolicyTestHelper.DUMMY.USER_ID,
				PasswordPolicyTestHelper.DUMMY.PASSWORD,
				passStatus
				 );
	}
	
	
}
