package nts.uk.ctx.sys.gateway.dom.login.password.userpassword;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.ViolationInfo;

public class ChangeLoginPasswordOfUserTest {
	
	@Injectable
	ChangeLoginPasswordOfUser.Require require;
	
	@Injectable
	LoginPasswordOfUser userPass;
	
	@Injectable
	PasswordPolicy passwordPolicy;
	
	@Test
	public void fail_invalidCurrentPassword() {
		
		new Expectations() {{
			
			userPass.matches(anyString);
			result = false;
			
			require.getLoginPasswordOfUser(anyString);
			result = Optional.of(userPass);
		}};
		
		NtsAssert.bundledBusinessException(Arrays.asList("Msg_302"), () -> {
			ChangeLoginPasswordOfUser.change(require, "user", "a", "b", "b");
		});
	}

	@Test
	public void fail_invalidConfirmPassword() {
		
		NtsAssert.bundledBusinessException(Arrays.asList("Msg_961"), () -> {
			ChangeLoginPasswordOfUser.change(require, "user", "a", "b");
		});
	}

	@Test
	public void failed_policyViolation() {
		
		new Expectations() {{
			
			passwordPolicy.validate((LoginPasswordOfUser) any, anyString);
			result = Arrays.asList(new ViolationInfo("Msg_000", ""));
			
			require.getPasswordPolicy();
			result = passwordPolicy;
		}};
		
		NtsAssert.bundledBusinessException(Arrays.asList("Msg_000"), () -> {
			ChangeLoginPasswordOfUser.change(require, "user", "a");
		});
	}
	
	@Test
	public void success() {
		
		new Expectations() {{
			
			passwordPolicy.validate((LoginPasswordOfUser) any, anyString);
			result = Collections.emptyList();
			
			require.getPasswordPolicy();
			result = passwordPolicy;
		}};
		
		NtsAssert.atomTask(
				() -> ChangeLoginPasswordOfUser.change(require, "user", "a"),
				any -> require.save(any.get()));
	}
}
