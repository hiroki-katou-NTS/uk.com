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
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy.ValidateOnLoginRequire;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyTestHelper.Dummy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog.PasswordChangeLogDetail;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity.PasswordComplexityRequirement;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class PasswordPolicyTest {

	@Injectable
	private ValidateOnLoginRequire require;
	
	@Test
	public void calcRemainingDays() {
		int validityPeriod = 5;
		int rangeDays = 2;
		PasswordPolicy policy = PasswordPolicyTestHelper.setValidityPeriod(rangeDays);
		
		String userId = "";
		List<PasswordChangeLogDetail> list = Arrays.asList(new PasswordChangeLogDetail(GeneralDateTime.now().addDays(validityPeriod), ""));
		val a = new PasswordChangeLog("", list);
		
		new Expectations() {{
			require.getPasswordChangeLog(userId);
			result = a;
		}};
		
		val result = (int)NtsAssert.Invoke.privateMethod(
				policy, 
				"calculateRemainingDays", 
				require,
				userId);
		assertThat(result).isEqualTo(validityPeriod + rangeDays);
	}
	
	
	@Test
	public void notUserPolicy() {
		val dummyInstance = PasswordPolicyTestHelper.dummyPolicy;
		ValidationResultOnLogin result = run(dummyInstance, Dummy.PASSSTATUES);
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.OK);
	}
	
	@Test
	public void resetPassword() {
		val dummyInstance = PasswordPolicyTestHelper.dummyPolicy;
		ValidationResultOnLogin result = run(dummyInstance, PassStatus.Reset);
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.RESET);
	}
	
	@Test
	public void initialPass() {
		val dummyInstance = PasswordPolicyTestHelper.dummyPolicy;
		ValidationResultOnLogin result = run(dummyInstance, PassStatus.InitPassword);
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.INITIAL);
	}

	@Test
	public void passComplex() {
		val dummyInstance = PasswordPolicyTestHelper.dummyPolicy;
		
		new MockUp<PasswordComplexityRequirement>() {
			@Mock
			public List<String> validatePassword(String password){
				return Arrays.asList("","");
			}
		};
		
		ValidationResultOnLogin result = run(dummyInstance, PassStatus.Official);
		
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.VIOLATED);
	}
	
	@Test
	public void success() {
		val dummyInstance = PasswordPolicyTestHelper.dummyPolicy;
		ValidationResultOnLogin result = run(dummyInstance, PassStatus.Official);
		assertThat(result.getStatus()).isEqualTo(ValidationResultOnLogin.Status.OK);
	}
	
	private ValidationResultOnLogin run(PasswordPolicy dummyInstance, PassStatus passStatus) {
		return (ValidationResultOnLogin)dummyInstance.validateOnLogin(
				require, 
				PasswordPolicyTestHelper.Dummy.USER_ID,
				PasswordPolicyTestHelper.Dummy.PASSWORD,
				passStatus
				 );
	}
	
	
}
