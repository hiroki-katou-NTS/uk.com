package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.AuthenticationFailuresLog;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockoutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LoginMethod;

@Getter
public class AccountLockPolicy extends AggregateRoot {
	
	// 契約コード
	private ContractCode contractCode;
	
	// 利用する
	private boolean isUse;
	
	// エラー回数
	private ErrorCount errorCount;
	
	// エラー間隔
	private LockInterval lockInterval;
	
	// ロックアウトメッセージ
	private LockOutMessage lockOutMessage;

	public AccountLockPolicy(ContractCode contractCode, ErrorCount errorCount, LockInterval lockInterval,
			LockOutMessage lockOutMessage, boolean isUse) {
		super();
		this.contractCode = contractCode;
		this.errorCount = errorCount;
		this.lockInterval = lockInterval;
		this.lockOutMessage = lockOutMessage;
		this.isUse = isUse;
	}

	public static AccountLockPolicy createFromJavaType(String contractCode, int errorCount, int lockInterval,
			String lockOutMessage, boolean isUse) {
		return new AccountLockPolicy(new ContractCode(contractCode), new ErrorCount(new BigDecimal(errorCount)),
				new LockInterval(lockInterval), new LockOutMessage(lockOutMessage), isUse);
	}
	
	/**
	 * ロックされているか
	 * @param require
	 * @param userId
	 * @return
	 */
	public boolean isLocked(Require require, String userId) {
		return isUse && require.getLockOutData(userId).isPresent();
	}
	
	/**
	 * 認証失敗時の検証
	 * @param require
	 * @param userId
	 * @return
	 */
	public Optional<LockoutData> validateAuthenticate(AuthenticationFailuresLog failuresLog) {
		int failureCount;
		if(lockInterval.v() == 0) {
			failureCount = failuresLog.countAll();
		}
		else {
			val startDateTime = GeneralDateTime.now().addMinutes(-lockInterval.valueAsMinutes());
			failureCount = failuresLog.countFrom(startDateTime);
		}
		
		if(failureCount >= errorCount.v().intValue() - 1) {
			return Optional.of(LockoutData.autoLock(contractCode, failuresLog.getUserId(), LoginMethod.NORMAL_LOGIN));
		}
		else {
			return Optional.empty();
		}
	}
	
	public static interface Require {
		
		Optional<LockoutData> getLockOutData(String userId);
		
		List<AuthenticationFailuresLog> getFailureLog(String userId);
		
		List<AuthenticationFailuresLog> getFailureLog(String userId, GeneralDateTime start, GeneralDateTime end);
	}
}
