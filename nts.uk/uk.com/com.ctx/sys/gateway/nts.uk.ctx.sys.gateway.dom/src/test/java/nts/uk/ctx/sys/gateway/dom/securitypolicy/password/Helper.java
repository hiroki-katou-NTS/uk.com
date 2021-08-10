package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.HashedLoginPassword;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.LoginPasswordOfUser;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.PasswordChangeLogDetail;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.PasswordState;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity.PasswordComplexityRequirement;

public class Helper {
	
	public static final String USER_ID = "user";
	
	public static GeneralDate todayIs(int y, int m, int d) {
		val today = GeneralDate.ymd(y, m, d);
		GeneralDateTime.FAKED_NOW = GeneralDateTime.midnightOf(today);
		return today;
	}
	
	public static class Policy {
		
		public static Builder builder() {
			return new Builder();
		}
		
		public static class Builder {
			private ContractCode contractCode;
			private boolean isUse;
			private PasswordComplexityRequirement complexityRequirement;
			private PasswordHistoryCount historyCount;
			private PasswordValidityPeriod validityPeriod;
			private NotificationPasswordChange notificationPasswordChange;
			private boolean initialPasswordChange;
			private boolean loginCheck;
			
			public Builder isUse(boolean value) {
				isUse = value;
				return this;
			}
			
			public Builder initialPasswordChange(boolean value) {
				initialPasswordChange = value;
				return this;
			}
			
			public Builder loginCheck(boolean value) {
				loginCheck = value;
				return this;
			}

			public Builder historyCount(int value) {
				historyCount = new PasswordHistoryCount(BigDecimal.valueOf(value));
				return this;
			}

			public Builder validityPeriod(int value) {
				validityPeriod = new PasswordValidityPeriod(BigDecimal.valueOf(value));
				return this;
			}

			public Builder notificationPasswordChange(int value) {
				notificationPasswordChange = new NotificationPasswordChange(BigDecimal.valueOf(value));
				return this;
			}
			
			public PasswordPolicy build() {
				return new PasswordPolicy(
						contractCode,
						notificationPasswordChange,
						loginCheck,
						initialPasswordChange,
						isUse,
						historyCount,
						validityPeriod,
						complexityRequirement);
			}
		}
	}
	
	public static class Violation {
		
		public static final ViolationInfo DUMMY = new ViolationInfo(null, null);
	}
	
	public static class UserPass {
		
		public static LoginPasswordOfUser empty(PasswordState state) {
			return new LoginPasswordOfUser(USER_ID, state, Collections.emptyList());
		}
		
		public static LoginPasswordOfUser of(PasswordChangeLogDetail... details) {
			return new LoginPasswordOfUser(USER_ID, PasswordState.OFFICIAL, Arrays.asList(details));
		}
		
		public static class Detail {
			
			public static PasswordChangeLogDetail of(GeneralDate date, String password) {
				return new PasswordChangeLogDetail(
						GeneralDateTime.midnightOf(date),
						HashedLoginPassword.hash(password, USER_ID));
			}
		}
	}
}
