package nts.uk.ctx.sys.gateway.dom.login.password.userpassword;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;

/**
 * ユーザーのログインパスワードを変更する
 */
public class ChangeLoginPasswordOfUser {
	
	public static AtomTask change(Require require, String userId, String currentPassword, String newPassword, String confirmPassword) {
		// TODO 
		return null;
	}
	
	public static AtomTask change(Require require, String userId, String newPassword, String confirmPassword) {
		// TODO 
		return null;
	}
	
	
	public static interface Require {
		
		PasswordPolicy getPasswordPolicy();
		
		Optional<LoginPasswordOfUser> getLoginPasswordOfUser(String userId);
		
		void save(LoginPasswordOfUser password);
	}
}
