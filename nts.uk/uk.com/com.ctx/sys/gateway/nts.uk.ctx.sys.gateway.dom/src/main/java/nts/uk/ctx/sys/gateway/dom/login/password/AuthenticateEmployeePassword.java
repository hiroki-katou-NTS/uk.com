package nts.uk.ctx.sys.gateway.dom.login.password;

import nts.uk.ctx.sys.shared.dom.user.FindUser;

/**
 * 社員コードとパスワードで認証する
 */
public class AuthenticateEmployeePassword {

	public static boolean authenticate(Require require, String companyId, String employeeCode, String password) {
		
		return FindUser.byEmployeeCode(require, companyId, employeeCode)
				.map(u -> u.comparePassword(password))
				.orElse(false);
	}
	
	public static interface Require extends FindUser.Require {}
}
