package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;

public class AccountLockPolicyTest {

	@Injectable
	private AccountLockPolicy.Require require;
	
	@Test
	public void Lock_UseLock_ExistLockOutData() {
		AccountLockPolicy target = AccountLockPolicyTestHelper.setUse(true);

		new Expectations() {{
			require.getLockOutData(AccountLockPolicyTestHelper.DUMMY.USER_ID);
			result = Optional.of(AccountLockPolicyTestHelper.DUMMY.LOCKOUT_DATA);
		}};
		
		val result = target.isLocked(require, AccountLockPolicyTestHelper.DUMMY.USER_ID);
		assertThat(result).isTrue();
	}
	
	@Test
	public void unLock_UserLock_NotExistLockOutData() {
		AccountLockPolicy target = AccountLockPolicyTestHelper.setUse(true);
		new Expectations() {{
			require.getLockOutData(AccountLockPolicyTestHelper.DUMMY.USER_ID);
			result = Optional.empty();
		}};
		
		val result = target.isLocked(require, AccountLockPolicyTestHelper.DUMMY.USER_ID);
		assertThat(result).isFalse();
	}
	
	
	@Test
	public void unLock() {
		AccountLockPolicy target = AccountLockPolicyTestHelper.setUse(false);
		val result = target.isLocked(require, AccountLockPolicyTestHelper.DUMMY.USER_ID);
		assertThat(result).isFalse();
	}

}
