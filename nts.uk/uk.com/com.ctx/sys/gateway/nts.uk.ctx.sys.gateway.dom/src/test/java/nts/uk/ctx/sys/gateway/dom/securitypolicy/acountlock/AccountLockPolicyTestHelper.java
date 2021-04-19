package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock;

import java.math.BigDecimal;

import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;

public class AccountLockPolicyTestHelper {
	static AccountLockPolicy accountLockPolicy =	 
			new AccountLockPolicy(
					new ContractCode(""), 
					new ErrorCount(BigDecimal.ZERO), 
					new LockInterval(0), 
					new LockOutMessage(""), 
					true);
	public static AccountLockPolicy setUse(boolean isUse) {
		return new AccountLockPolicy(
				Dummy.contractCode,
				Dummy.errorCount, 
				Dummy.lockInterval, 
				Dummy.lockOutMessage,
				isUse);
	}
	
	public static AccountLockPolicy setContractCode(String contractCD) {
		return new AccountLockPolicy(
				new ContractCode(contractCD),
				Dummy.errorCount, 
				Dummy.lockInterval, 
				Dummy.lockOutMessage,
				Dummy.isUse);
	}
	
	public static AccountLockPolicy setErrorCount(int errorCount) {
		return new AccountLockPolicy(
				Dummy.contractCode,
				new ErrorCount(BigDecimal.valueOf(errorCount)), 
				Dummy.lockInterval, 
				Dummy.lockOutMessage,
				Dummy.isUse);
	}
	
	static class Dummy{
		static ContractCode contractCode = new ContractCode("");
		static ErrorCount errorCount = new ErrorCount(BigDecimal.ZERO);
		static LockInterval lockInterval = new LockInterval(0);
		static LockOutMessage lockOutMessage = new LockOutMessage("");
		static boolean isUse = true;
	}
}
