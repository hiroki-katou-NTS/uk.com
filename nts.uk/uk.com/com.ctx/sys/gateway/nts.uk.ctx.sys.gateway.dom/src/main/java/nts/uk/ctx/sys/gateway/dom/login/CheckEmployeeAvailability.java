package nts.uk.ctx.sys.gateway.dom.login;

import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;

/**
 * 社員がログインできるかチェックする
 */
public class CheckEmployeeAvailability {

	public static void check(Require require, IdentifiedEmployeeInfo identified) {
		// ログインできるユーザかチェックする
		CheckUserAvailability.check(require, identified);
	}
		
	public static interface Require extends 
			AccountLockPolicy.Require, 
			CheckUserAvailability.Require {
		
	}
}
