package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity.PasswordComplexityRequirement;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class PasswordPolicyTestHelper {
	
	public static PasswordPolicy setValidityPeriod(int period) {
		return new PasswordPolicy(
				DUMMY.CONTRACT_CD, 
				DUMMY.NOTICE_PASSWORD_CHANGE, 
				DUMMY.IS_LOGIN, 
				DUMMY.INITIAL_PASSWORD_CHANGE, 
				DUMMY.IS_USE, 
				DUMMY.PASSWORD_HISTORY_COUNT, 
				new PasswordValidityPeriod(BigDecimal.valueOf(period)), 
				DUMMY.PASSWORD_COMPLEX
				);
	}
	
	public static PasswordPolicy setUse(boolean isUse) {
		return new PasswordPolicy(
				DUMMY.CONTRACT_CD, 
				DUMMY.NOTICE_PASSWORD_CHANGE, 
				DUMMY.IS_LOGIN, 
				DUMMY.INITIAL_PASSWORD_CHANGE, 
				isUse, 
				DUMMY.PASSWORD_HISTORY_COUNT, 
				DUMMY.PASSWORD_VALIDATE_PERIOD,
				DUMMY.PASSWORD_COMPLEX
				);
	}
	
	public static class DUMMY{
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
		public static PasswordPolicy PASSWORD_POLICY = new PasswordPolicy(DUMMY.CONTRACT_CD,DUMMY.NOTICE_PASSWORD_CHANGE,DUMMY.IS_LOGIN,DUMMY.INITIAL_PASSWORD_CHANGE,DUMMY.IS_USE,DUMMY.PASSWORD_HISTORY_COUNT,DUMMY.PASSWORD_VALIDATE_PERIOD,DUMMY.PASSWORD_COMPLEX);
		public static List<String> STRING_LIST = Arrays.asList("","");
	}
}
