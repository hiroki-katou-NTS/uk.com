package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

/**
 * 社員コードとパスワードで認証する
 */
public class PasswordAuthenticateWithEmployeeCode {

	public static PasswordAuthenticationResult authenticate(
			Require require,
			IdentifiedEmployeeInfo identified,
			String password) {
		
		// ロックアウトされている社員は認証させてはいけない
		require.getAccountLockPolicy(identified.getTenantCode())
				.ifPresent(policy -> {
					if (policy.isLocked(require, identified.getUserId())) {
						throw new BusinessException(new RawErrorMessage(policy.getLockOutMessage().v()));
					}
				});
		
		// パスワード認証
		val user = identified.getUser();
		if (!user.isCorrectPassword(password)) {
			val atomTask = FailedPasswordAuthenticate.failed(require, identified, password);
			return PasswordAuthenticationResult.failure(atomTask);
		}

		// パスワードポリシーへの準拠チェック
		val passwordPolicy = require.getPasswordPolicy(identified.getTenantCode());
		val passwordPolicyResult = passwordPolicy.map(p -> p.validateOnLogin(require, user.getUserID(), password, user.getPassStatus()))
												.orElse(ValidationResultOnLogin.ok());
		
		return PasswordAuthenticationResult.success(passwordPolicyResult);
	}
	
	public static interface Require extends
			FailedPasswordAuthenticate.Require,
			PasswordPolicy.ValidateOnLoginRequire, 
			EmployeeIdentify.Require{

		Optional<AccountLockPolicy> getAccountLockPolicy(String tenantCode);
		
		Optional<PasswordPolicy> getPasswordPolicy(String tenantCode);
	}
}
