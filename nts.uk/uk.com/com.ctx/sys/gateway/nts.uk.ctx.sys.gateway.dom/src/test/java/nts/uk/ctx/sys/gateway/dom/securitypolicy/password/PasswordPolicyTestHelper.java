package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.HashedLoginPassword;
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
		public final static PassStatus PASSSTATUES = PassStatus.Official; 
		public final static ContractCode CONTRACT_CD = new ContractCode("contractCode");
		public final static String USER_ID = "user";
		public final static HashedLoginPassword PASSWORD = HashedLoginPassword.hash("password", USER_ID);
		public final static NotificationPasswordChange NOTICE_PASSWORD_CHANGE = new NotificationPasswordChange(BigDecimal.valueOf(999999999));
		public final static boolean IS_USE = true;
		public final static boolean IS_LOGIN = true;
		public final static boolean INITIAL_PASSWORD_CHANGE = true;
		public final static PasswordHistoryCount PASSWORD_HISTORY_COUNT = new PasswordHistoryCount(BigDecimal.valueOf(999999999));
		public final static PasswordValidityPeriod PASSWORD_VALIDATE_PERIOD = new PasswordValidityPeriod(BigDecimal.valueOf(999999999));
		public final static PasswordComplexityRequirement PASSWORD_COMPLEX = PasswordComplexityRequirement.createFromJavaType(0, 0, 0, 0);
		public final static PasswordPolicy PASSWORD_POLICY = new PasswordPolicy(DUMMY.CONTRACT_CD,DUMMY.NOTICE_PASSWORD_CHANGE,DUMMY.IS_LOGIN,DUMMY.INITIAL_PASSWORD_CHANGE,DUMMY.IS_USE,DUMMY.PASSWORD_HISTORY_COUNT,DUMMY.PASSWORD_VALIDATE_PERIOD,DUMMY.PASSWORD_COMPLEX);
		public final static List<String> STRING_LIST = Arrays.asList("","");
	}
}
