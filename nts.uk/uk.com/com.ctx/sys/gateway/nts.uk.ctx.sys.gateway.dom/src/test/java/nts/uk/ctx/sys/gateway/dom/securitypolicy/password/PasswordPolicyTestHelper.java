package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import java.math.BigDecimal;

import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity.PasswordComplexityRequirement;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class PasswordPolicyTestHelper {
	
	public static PasswordPolicy dummyPolicy = new PasswordPolicy(
			Dummy.CONTRACT_CD, 
			Dummy.NOTICE_PASSWORD_CHANGE, 
			Dummy.IS_LOGIN, 
			Dummy.INITIAL_PASSWORD_CHANGE, 
			Dummy.IS_USE, 
			Dummy.PASSWORD_HISTORY_COUNT, 
			Dummy.PASSWORD_VALIDATE_PERIOD,
			Dummy.PASSWORD_COMPLEX
			);
	
	public static PasswordPolicy setValidityPeriod(int period) {
		return new PasswordPolicy(
				Dummy.CONTRACT_CD, 
				Dummy.NOTICE_PASSWORD_CHANGE, 
				Dummy.IS_LOGIN, 
				Dummy.INITIAL_PASSWORD_CHANGE, 
				Dummy.IS_USE, 
				Dummy.PASSWORD_HISTORY_COUNT, 
				new PasswordValidityPeriod(BigDecimal.valueOf(period)), 
				Dummy.PASSWORD_COMPLEX
				);
	}
	
	public static PasswordPolicy setUse(boolean isUse) {
		return new PasswordPolicy(
				Dummy.CONTRACT_CD, 
				Dummy.NOTICE_PASSWORD_CHANGE, 
				Dummy.IS_LOGIN, 
				Dummy.INITIAL_PASSWORD_CHANGE, 
				isUse, 
				Dummy.PASSWORD_HISTORY_COUNT, 
				Dummy.PASSWORD_VALIDATE_PERIOD,
				Dummy.PASSWORD_COMPLEX
				);
	}
	
	public static class Dummy{
		public static PassStatus PASSSTATUES = PassStatus.Official; 
		public static ContractCode CONTRACT_CD = new ContractCode("");
		public static String USER_ID = "user";
		public static String PASSWORD = "passwprd";
		public static NotificationPasswordChange NOTICE_PASSWORD_CHANGE = new NotificationPasswordChange(BigDecimal.ZERO);
		public static boolean IS_USE = true;
		public static boolean IS_LOGIN = true;
		public static boolean INITIAL_PASSWORD_CHANGE = true;
		public static PasswordHistoryCount PASSWORD_HISTORY_COUNT = new PasswordHistoryCount(BigDecimal.ZERO);
		public static PasswordValidityPeriod PASSWORD_VALIDATE_PERIOD = new PasswordValidityPeriod(BigDecimal.ZERO);
		public static PasswordComplexityRequirement PASSWORD_COMPLEX = PasswordComplexityRequirement.createFromJavaType(0, 0, 0, 0);
	}
}
