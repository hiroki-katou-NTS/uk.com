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

	public static AtomTask failed(Require require, String userId) {
		
		val failuresLog = require.getAuthenticationFailuresLog(userId);
		failuresLog.failedNow();
		
		String contractCode = require.getLoginUserContractCode();
		
		val lockOpt = require.getAccountLockPolicy(contractCode)
				.flatMap(p -> p.validate(failuresLog));
		
		return AtomTask.of(() -> {
			
			require.save(failuresLog);
			
			lockOpt.ifPresent(lock -> {
				require.save(lock);
			});
		});
	}
	
	public static interface Require {
		
		String getLoginUserContractCode();
		
		AuthenticationFailuresLog getAuthenticationFailuresLog(String userId);
		
		Optional<AccountLockPolicy> getAccountLockPolicy(String contractCode);
		
		void save(AuthenticationFailuresLog failuresLog);
		
		void save(LockOutData lockOutData);
	}
}
