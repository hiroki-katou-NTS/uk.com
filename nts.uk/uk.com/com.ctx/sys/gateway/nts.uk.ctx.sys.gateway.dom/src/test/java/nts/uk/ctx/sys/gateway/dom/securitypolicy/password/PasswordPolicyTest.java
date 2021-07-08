package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.LoginPasswordOfUser;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.PasswordChangeLogDetail;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.PasswordState;

public class PasswordPolicyTest {

	
	@Test
	public void calcRemainingDays() {
		int passwordChangeLastDays = -5;
		int rangeDays = 2;
		
		List<PasswordChangeLogDetail> list = Arrays.asList(
				new PasswordChangeLogDetail(GeneralDateTime.now().addDays(passwordChangeLastDays), PasswordPolicyTestHelper.DUMMY.PASSWORD));
		
		
		val userPassword = new LoginPasswordOfUser(PasswordPolicyTestHelper.DUMMY.USER_ID, PasswordState.OFFICIAL, list);
		
		val result = (int)NtsAssert.Invoke.privateMethod(
				PasswordPolicyTestHelper.setValidityPeriod(rangeDays), 
				"calculateRemainingDays", 
				userPassword);
		assertThat(result).isEqualTo(passwordChangeLastDays + rangeDays);
	}
	
}
