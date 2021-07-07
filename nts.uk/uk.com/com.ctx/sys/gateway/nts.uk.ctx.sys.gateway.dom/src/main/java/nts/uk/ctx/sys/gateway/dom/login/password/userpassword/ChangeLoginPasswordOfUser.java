package nts.uk.ctx.sys.gateway.dom.login.password.userpassword;

import nts.arc.task.tran.AtomTask;

/**
 * ユーザーのログインパスワードを変更する
 */
public class ChangeLoginPasswordOfUser {
	
	public static AtomTask change(Require require, String userId, String currentPassword, String newPassword, String confirmPassword) {
		// TODO 
		return null;
	}
	
	public static AtomTask change(Require require, String userId, String currentPassword, String newPassword) {
		// TODO 
		return null;
	}
	
	
	public static interface Require {
		
	}
}
