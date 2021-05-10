package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock;

import java.math.BigDecimal;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockoutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LoginMethod;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;

public class AccountLockPolicyTestHelper {
	static LockoutData lockoutData =  
			new LockoutData(
					new ContractCode("0"), 
					"", 
					GeneralDateTime.now(), 
					LockType.AUTO_LOCK, 
					LoginMethod.NORMAL_LOGIN);
	
			
	public static AccountLockPolicy setUse(boolean isUse) {
		return new AccountLockPolicy(
				DUMMY.CONTRACT_CD,
				DUMMY.ERROR_COUNT, 
				DUMMY.LOCK_INTERVAL, 
				DUMMY.LOCKOUT_MESSAGE,
				isUse);
	}
	
	public static AccountLockPolicy setContractCode(String contractCD) {
		return new AccountLockPolicy(
				new ContractCode(contractCD),
				DUMMY.ERROR_COUNT, 
				DUMMY.LOCK_INTERVAL, 
				DUMMY.LOCKOUT_MESSAGE,
				DUMMY.IS_USE);
	}
	
	public static AccountLockPolicy setErrorCount(int errorCount) {
		return new AccountLockPolicy(
				DUMMY.CONTRACT_CD,
				new ErrorCount(BigDecimal.valueOf(errorCount)), 
				DUMMY.LOCK_INTERVAL, 
				DUMMY.LOCKOUT_MESSAGE,
				DUMMY.IS_USE);
	}
	
	static class DUMMY{
		static ContractCode CONTRACT_CD = new ContractCode("contractCd");
		static String USER_ID = "userId";
		static ErrorCount ERROR_COUNT = new ErrorCount(BigDecimal.ZERO);
		static LockInterval LOCK_INTERVAL = new LockInterval(0);
		static LockOutMessage LOCKOUT_MESSAGE = new LockOutMessage("message");
		static boolean IS_USE = true;
		static LockoutData LOCKOUT_DATA = AccountLockPolicyTestHelper.lockoutData;
	}
}
