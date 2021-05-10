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
					DUMMY.CONTRACT_CD, 
					DUMMY.USER_ID, 
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
		static final ContractCode CONTRACT_CD = new ContractCode("contractCd");
		static final String USER_ID = "userId";
		static final ErrorCount ERROR_COUNT = new ErrorCount(BigDecimal.valueOf(999999999));
		static final LockInterval LOCK_INTERVAL = new LockInterval(9999);
		static final GeneralDateTime LOCKOUT_DATETIME = GeneralDateTime.min();
		static final LockOutMessage LOCKOUT_MESSAGE = new LockOutMessage("message");
		static final LockType LOG_TYPE = LockType.AUTO_LOCK;
		static final LoginMethod LOGING_METHOD = LoginMethod.NORMAL_LOGIN;
		static final boolean IS_USE = true;
		 
		static final LockoutData LOCKOUT_DATA = new LockoutData(
				DUMMY.CONTRACT_CD, 
				DUMMY.USER_ID, 
				DUMMY.LOCKOUT_DATETIME, 
				DUMMY.LOG_TYPE, 
				DUMMY.LOGING_METHOD);
	}
}
