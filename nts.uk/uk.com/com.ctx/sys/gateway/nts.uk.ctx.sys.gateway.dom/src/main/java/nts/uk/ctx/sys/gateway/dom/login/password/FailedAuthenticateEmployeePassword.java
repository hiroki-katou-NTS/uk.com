package nts.uk.ctx.sys.gateway.dom.login.password;

import java.util.Optional;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutData;

/**
 * パスワード認証に失敗した
 */
public class FailedAuthenticateEmployeePassword {

	public AtomTask failed(Require require, String userId) {
		
		val failuresLog = require.getAuthenticationFailuresLog(userId);
		
		String contractCode = require.getLoginUserContractCode();
		
		Runnable task = require.getAccountLockPolicy(contractCode)
				.flatMap(p -> p.validate(failuresLog))
				.map(lock -> (Runnable)() -> require.save(lock))
				.orElse(() -> {});
		
		return AtomTask.of(task);
	}
	
	public static interface Require {
		
		String getLoginUserContractCode();
		
		AuthenticationFailuresLog getAuthenticationFailuresLog(String userId);
		
		Optional<AccountLockPolicy> getAccountLockPolicy(String contractCode);
		
		void save(LockOutData lockOutData);
	}
}
