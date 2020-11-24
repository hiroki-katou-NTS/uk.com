package nts.uk.ctx.sys.gateway.dom.login.password;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.PasswordValidationOnLogin;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.User;

/**
 * 社員コードとパスワードで認証する
 */
public class AuthenticateEmployeePassword {

	public static AuthenticateEmployeePasswordResult authenticate(
			Require require,
			String tenantCode,
			String companyId,
			String employeeCode,
			String password) {
		
		// 識別
		IdentifiedEmployeeInfo identified;
		{
			val opt = identify(require, companyId, employeeCode);
			if (!opt.isPresent()) {
				return AuthenticateEmployeePasswordResult.notFoundUser();
			}
			identified = opt.get();
		}
		
		val user = identified.getUser();
		
		// 認証
		if (!user.isCorrectPassword(password)) {
			val atomTask = FailedAuthenticateEmployeePassword.failed(require, user.getUserID());
			return AuthenticateEmployeePasswordResult.failedAuthentication(atomTask);
		}
		
		// パスワードリセット
		if (user.getPassStatus().isReset()) {
			return AuthenticateEmployeePasswordResult.succeededWithResetPassword(identified);
		}
		
		// パスワードポリシー
		val passwordPolicyResult = checkPasswordPolicy(require, tenantCode, password, user);
		
		return AuthenticateEmployeePasswordResult.succeeded(identified, passwordPolicyResult);
	}
	
	/**
	 * 社員コードにより識別する
	 * @param require
	 * @param companyId
	 * @param employeeCode
	 * @return
	 */
	private static Optional<IdentifiedEmployeeInfo> identify(Require require, String companyId, String employeeCode) {

		val employee = require.getEmployeeDataMngInfoImportByEmployeeCode(companyId, employeeCode);
		if (!employee.isPresent()) {
			return Optional.empty();
		}
		
		val user = require.getUserByPersonId(employee.get().getPersonId());
		if (!user.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(new IdentifiedEmployeeInfo(employee.get(), user.get()));
	}
	
	/**
	 * パスワードポリシーへの準拠チェック
	 * @param require
	 * @param tenantCode
	 * @param password
	 * @param user
	 * @return
	 */
	private static PasswordValidationOnLogin checkPasswordPolicy(
			Require require,
			String tenantCode,
			String password,
			User user) {
		
		return require.getPasswordPolicy(tenantCode)
				.map(p -> p.validateOnLogin(require, user.getUserID(), password, user.getPassStatus()))
				.orElse(PasswordValidationOnLogin.ok());
	}
	
	
	public static interface Require extends
			FailedAuthenticateEmployeePassword.Require,
			PasswordPolicy.ValidateOnLoginRequire {
		
		Optional<EmployeeDataMngInfoImport> getEmployeeDataMngInfoImportByEmployeeCode(String companyId, String employeeCode);
		
		Optional<User> getUserByPersonId(String personId);
		
		Optional<PasswordPolicy> getPasswordPolicy(String tenantCode);
	}
}
