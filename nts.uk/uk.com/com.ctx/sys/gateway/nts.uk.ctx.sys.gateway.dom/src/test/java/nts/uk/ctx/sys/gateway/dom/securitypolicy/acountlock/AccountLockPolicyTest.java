package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.MockUp;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy.Require;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockoutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LoginMethod;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;

public class AccountLockPolicyTest {

	@Injectable
	private AccountLockPolicy.Require require;
	
	@Test
	public void Lock_UseLock_ExistLockOutData() {
		AccountLockPolicy target = AccountLockPolicyTestHelper.accountLockPolicy;
		LockoutData dummyResult =  
				new LockoutData(
						new ContractCode("0"), 
						"", 
						GeneralDateTime.now(), 
						LockType.AUTO_LOCK, 
						LoginMethod.NORMAL_LOGIN);
		new Expectations() {{
			require.getLockOutData("");
			result = Optional.of(dummyResult);
		}};
		
		String userID = "";
		val result = target.isLocked(require, userID);
		assertThat(result).isTrue();
	}
	
	@Test
	public void unLock_UserLock_NotExistLockOutData() {
		AccountLockPolicy target = AccountLockPolicyTestHelper.accountLockPolicy;
		new Expectations() {{
			require.getLockOutData("");
			result = Optional.empty();
		}};
		
		String userID = "";
		val result = target.isLocked(require, userID);
		assertThat(result).isFalse();
	}
	
	
	@Test
	public void unLock() {
		AccountLockPolicy target = AccountLockPolicyTestHelper.accountLockPolicy;
		
		String userID = "1";
		val result = target.isLocked(require, userID);
		assertThat(result).isFalse();
	}
	
	@Test
	public void ableAccept() {
		String contractCode = "cont";
		String userId = "user";
		AccountLockPolicy target = AccountLockPolicyTestHelper.setContractCode(contractCode);
		new MockUp<AccountLockPolicy>() {
			private int countFail(Require require, String userID) {
				return 0;
			}
		};
		
		val result = target.validateAuthenticate(require, userId);
		assertThat(result.get().getContractCode().toString()).isEqualTo(contractCode);
		assertThat(result.get().getUserId()).isEqualTo(userId);
		assertThat(result.get().getLoginMethod()).isEqualTo(LoginMethod.NORMAL_LOGIN);
	}
	
	@Test
	public void unAbleAccept() {
		AccountLockPolicy target = AccountLockPolicyTestHelper.setErrorCount(5);
		new MockUp<AccountLockPolicy>() {
			private int countFail(Require require, String userID) {
				return 0;
			}
		};
		
		val results = target.validateAuthenticate(require, "");
		assertThat(!results.isPresent()).isTrue();
	}

}
