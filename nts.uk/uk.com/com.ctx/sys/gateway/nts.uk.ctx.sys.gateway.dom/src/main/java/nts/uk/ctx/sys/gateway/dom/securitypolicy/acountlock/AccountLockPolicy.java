package nts.uk.ctx.sys.gateway.dom.securitypolicy;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;

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

}
