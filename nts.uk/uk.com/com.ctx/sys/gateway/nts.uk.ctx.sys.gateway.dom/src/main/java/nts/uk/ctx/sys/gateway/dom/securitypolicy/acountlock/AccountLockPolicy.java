package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.password.AuthenticationFailuresLog;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutData;

@Getter
public class AccountLockPolicy extends AggregateRoot {
	private ContractCode contractCode;
	private ErrorCount errorCount;
	private LockInterval lockInterval;
	private LockOutMessage lockOutMessage;
	private boolean isUse;

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
	 * 検証する
	 * @param failuresLog
	 * @return
	 */
	public Optional<LockOutData> validate(AuthenticationFailuresLog failuresLog) {
		
		val baseTime = GeneralDateTime.now().addMinutes(-lockInterval.valueAsMinutes());
		int failuresCount = failuresLog.countFrom(baseTime);
		
		if (failuresCount >= errorCount.v().intValue()) {
			val locked = LockOutData.autoLock(failuresLog.getUserId(), contractCode);
			return Optional.of(locked);
		}
		
		return Optional.empty();
	}
}
