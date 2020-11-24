package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity.PasswordComplexityRequirement;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.PasswordValidationOnLogin;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.PasswordValidationOnLogin.Status;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

@Getter
public class PasswordPolicy extends AggregateRoot {
	private ContractCode contractCode;
	private NotificationPasswordChange notificationPasswordChange;
	private boolean loginCheck;
	private boolean initialPasswordChange;
	private boolean isUse;
	private PasswordHistoryCount historyCount;
	private PasswordValidityPeriod validityPeriod;
	private PasswordComplexityRequirement complexityRequirement;

	public PasswordPolicy(ContractCode contractCode, NotificationPasswordChange notificationPasswordChange,
			boolean loginCheck, boolean initialPasswordChange, boolean isUse, PasswordHistoryCount historyCount,
			PasswordValidityPeriod validityPeriod, PasswordComplexityRequirement complexityRequirement) {
		super();
		this.contractCode = contractCode;
		this.notificationPasswordChange = notificationPasswordChange;
		this.loginCheck = loginCheck;
		this.initialPasswordChange = initialPasswordChange;
		this.isUse = isUse;
		this.historyCount = historyCount;
		this.validityPeriod = validityPeriod;
		this.complexityRequirement = complexityRequirement;
	}

	public static PasswordPolicy createFromJavaType(String contractCode, int notificationPasswordChange,
			boolean loginCheck, boolean initialPasswordChange, boolean isUse, int historyCount, 
			int validityPeriod, PasswordComplexityRequirement complexityRequirement) {
		return new PasswordPolicy(new ContractCode(contractCode),
				new NotificationPasswordChange(new BigDecimal(notificationPasswordChange)), loginCheck,
				initialPasswordChange, isUse, new PasswordHistoryCount(new BigDecimal(historyCount)),
				new PasswordValidityPeriod(new BigDecimal(validityPeriod)),
				complexityRequirement);

	}

	public PasswordValidationOnLogin validateOnLogin(
			ValidateOnLoginRequire require,
			String userId,
			String password,
			PassStatus passwordStatus) {
		
		if (!loginCheck || !isUse) {
			return PasswordValidationOnLogin.ok();
		}
		
		// 初期パスワード
		if (initialPasswordChange && passwordStatus.equals(PassStatus.InitPassword)) {
			return PasswordValidationOnLogin.error(Status.INITIAL);
		}
			
		// 文字構成をチェック
		if (!complexityRequirement.validatePassword(password)) {
			return PasswordValidationOnLogin.error(Status.VIOLATED);
		}
		
		// 有効期限をチェック
		if (!validityPeriod.isUnlimited()) {
			int remainingDays = calculateRemainingDays(require, userId);
			
			// 有効期限切れ
			if (remainingDays < 0) {
				return PasswordValidationOnLogin.error(Status.EXPIRED);
			}
			
			// 期限切れが近い通知
			if (notificationPasswordChange.needsNotify(remainingDays)) {
				return PasswordValidationOnLogin.expiresSoon(remainingDays);
			}
		}

		return PasswordValidationOnLogin.ok();
	}
	
	public static interface ValidateOnLoginRequire {
		
		PasswordChangeLog getPasswordChangeLog(String userId);
	}
	
	/**
	 * パスワードの残り有効日数を求める
	 * @param user
	 * @return
	 */
	private int calculateRemainingDays(ValidateOnLoginRequire require, String userId) {
		
		val changeLog = require.getPasswordChangeLog(userId);
		int ageInDays = changeLog.latestLog().ageInDays();
		
		return validityPeriod.v().intValue() - ageInDays;
	}
}
