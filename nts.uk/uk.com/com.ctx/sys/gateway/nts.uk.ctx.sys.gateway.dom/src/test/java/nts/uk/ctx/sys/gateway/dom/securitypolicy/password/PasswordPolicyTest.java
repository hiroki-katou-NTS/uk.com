package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import static nts.arc.time.GeneralDate.*;
import static nts.uk.ctx.sys.gateway.dom.securitypolicy.password.Helper.*;
import static nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin.Status.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Mock;
import mockit.MockUp;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.LoginPasswordOfUser;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.PasswordState;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.Helper.Policy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.Helper.UserPass;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.Helper.Violation;

@RunWith(Enclosed.class)
public class PasswordPolicyTest {

	public static class violatedOnLogin {
		
		@Test
		public void reset() {
			
			val target = Policy.builder().isUse(true).build();
			val userPass = UserPass.empty(PasswordState.RESET);
			
			val actual = target.violatedOnLogin(userPass, null);
			
			assertThat(actual.getStatus()).isEqualTo(RESET);
		}
		
		@Test
		public void initial() {
			
			val target = Policy.builder().isUse(true).initialPasswordChange(true).build();
			val userPass = UserPass.empty(PasswordState.INITIAL);
			
			val actual = target.violatedOnLogin(userPass, null);
			
			assertThat(actual.getStatus()).isEqualTo(INITIAL);
		}
		
		@Test
		public void complexityError() {

			val target = Policy.builder().isUse(true).loginCheck(true).build();
			
			val mockedTarget = new MockUp<PasswordPolicy>(target) {
				@Mock
				public List<ViolationInfo> validate(LoginPasswordOfUser changeLog, String currentPasswordPlainText) {
					return Arrays.asList(Violation.DUMMY);
				}
			}.getMockInstance();
			
			val userPass = UserPass.empty(PasswordState.OFFICIAL);

			val actual = mockedTarget.violatedOnLogin(userPass, null);

			assertThat(actual.getStatus()).isEqualTo(VIOLATED);
		}
		
		@Test
		public void expired() {
			
			val target = Policy.builder().isUse(true).validityPeriod(2).build();

			todayIs(2000, 4, 10);
			val userPass = UserPass.of(UserPass.Detail.of(ymd(2000, 4, 1), "a"));
			
			val actual = target.violatedOnLogin(userPass, null);
			
			assertThat(actual.getStatus()).isEqualTo(EXPIRED);
		}
		
		@Test
		public void expiresSoon() {
			
			val target = Policy.builder().isUse(true).validityPeriod(10).notificationPasswordChange(3).build();

			todayIs(2000, 4, 9);
			val userPass = UserPass.of(UserPass.Detail.of(ymd(2000, 4, 1), "a"));
			
			val actual = target.violatedOnLogin(userPass, null);
			
			assertThat(actual.getStatus()).isEqualTo(EXPIRES_SOON);
		}
		
		@Test
		public void ok_notUse() {
			
			val target = Policy.builder().isUse(false).build();
			
			val actual = target.violatedOnLogin(null, null);
			
			assertThat(actual.getStatus()).isEqualTo(OK);
		}
		
		@Test
		public void ok() {
			
			val target = Policy.builder().isUse(true).validityPeriod(0).build();
			val userPass = UserPass.empty(PasswordState.OFFICIAL);

			val actual = target.violatedOnLogin(userPass, null);
			
			assertThat(actual.getStatus()).isEqualTo(OK);
		}
	}
	
	public static class duplicatesLatestPassword {
		
		private static final LoginPasswordOfUser USER_PASS = UserPass.of(
				UserPass.Detail.of(ymd(2000, 1, 1), "A"),
				UserPass.Detail.of(ymd(2000, 2, 1), "B"),
				UserPass.Detail.of(ymd(2000, 3, 1), "C"),
				UserPass.Detail.of(ymd(2000, 4, 1), "A"));

		@Test
		public void duplicated() {
			val target = Policy.builder().historyCount(3).build();
			assertDuplicates(target, true);
		}
		
		@Test
		public void notDuplicated() {
			val target = Policy.builder().historyCount(2).build();
			assertDuplicates(target, false);
		}
		
		private void assertDuplicates(PasswordPolicy target, boolean expected) {
			boolean actual = NtsAssert.Invoke.privateMethod(target, "duplicatesLatestPassword", USER_PASS);
			assertThat(actual).isEqualTo(expected);
		}
	}
	
	public static class calculateRemainingDays {
		
		@Test
		public void test() {

			val target = Policy.builder().validityPeriod(3).build();
			
			val userPass = UserPass.of(UserPass.Detail.of(ymd(2000, 1, 1), "A"));
			todayIs(2000, 1, 3);
			
			int actual = NtsAssert.Invoke.privateMethod(target, "calculateRemainingDays", userPass);
			
			assertThat(actual).isEqualTo(1);
		}
	}
}
