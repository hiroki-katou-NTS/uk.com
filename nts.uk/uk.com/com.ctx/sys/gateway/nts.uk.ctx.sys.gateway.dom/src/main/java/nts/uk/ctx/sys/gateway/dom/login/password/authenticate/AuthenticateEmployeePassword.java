package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.FailedAuthenticateEmployeePassword.Require;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

/**
 * 社員コードとパスワードで認証する
 */
public class AuthenticateEmployeePassword {

	public static AuthenticateResultEmployeePassword authenticate(
			Require require,
			IdentifiedEmployeeInfo identified,
			String password) {
		
		// パスワード認証
		val user = identified.getUser();
		if (!user.isCorrectPassword(password)) {
			val atomTask = FailedAuthenticateEmployeePassword.failed(require, user.getUserID());
			return AuthenticateResultEmployeePassword.failedAuthentication(atomTask);
		}

		// パスワードポリシーへの準拠チェック
		val passwordPolicy = require.getPasswordPolicy(identified.getTenantCode());
		
		val passwordPolicyResult = passwordPolicy.map(p -> p.validateOnLogin(require, user.getUserID(), password, user.getPassStatus()))
												.orElse(ValidationResultOnLogin.ok());
		
		if(passwordPolicyResult.isProblem()) {
			return AuthenticateResultEmployeePassword.succeededWithChangePassword(identified);
		}
		
		return AuthenticateResultEmployeePassword.succeeded(identified, passwordPolicyResult);
	}
	
	public static interface Require extends
			FailedAuthenticateEmployeePassword.Require,
			PasswordPolicy.ValidateOnLoginRequire, 
			EmployeeIdentify.Require{
		
		Optional<PasswordPolicy> getPasswordPolicy(String tenantCode);
	}
}
