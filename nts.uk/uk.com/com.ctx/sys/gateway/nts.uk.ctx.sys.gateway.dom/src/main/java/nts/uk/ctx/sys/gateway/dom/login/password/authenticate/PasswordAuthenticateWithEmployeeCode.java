package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

/**
 * 社員コードとパスワードで認証する
 */
public class PasswordAuthenticateWithEmployeeCode {

	public static PasswordAuthenticateResult authenticate(
			Require require,
			IdentifiedEmployeeInfo identified,
			String password) {
		
		// パスワード認証
		val user = identified.getUser();
		if (!user.isCorrectPassword(password)) {
			val atomTask = FailedPasswordAuthenticate.failed(require, identified, password);
			return PasswordAuthenticateResult.failure(atomTask);
		}

		// パスワードポリシーへの準拠チェック
		val passwordPolicy = require.getPasswordPolicy(identified.getTenantCode());
		val passwordPolicyResult = passwordPolicy.map(p -> p.validateOnLogin(require, user.getUserID(), password, user.getPassStatus()))
												.orElse(ValidationResultOnLogin.ok());
		
		return PasswordAuthenticateResult.success(passwordPolicyResult);
	}
	
	public static interface Require extends
			FailedPasswordAuthenticate.Require,
			PasswordPolicy.ValidateOnLoginRequire, 
			EmployeeIdentify.Require{
		
		Optional<PasswordPolicy> getPasswordPolicy(String tenantCode);
	}
}
