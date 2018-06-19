package nts.uk.ctx.sys.auth.pub.user;

/**
 * The Interface CheckBeforePasswordPublisher.
 */
public interface CheckBeforePasswordPublisher {

	/**
	 * Check before change password.
	 *
	 * @param userId the user id
	 * @param currentPass the current pass
	 * @param newPass the new pass
	 * @param reNewPass the re new pass
	 * @return the check before change pass output
	 */
	//パスワード変更前チェック  request list 383
	CheckBeforeChangePassOutput checkBeforeChangePassword(String userId,String currentPass, String newPass, String reNewPass);

	/**
	 * Password policy check.
	 *
	 * @param userId the user id
	 * @param newPass the new pass
	 * @param contractCode the contract code
	 * @return the check before change pass output
	 */
	//check passPolicy
	CheckBeforeChangePassOutput passwordPolicyCheck(String userId, String newPass, String contractCode);
	
	/**
	 * Password policy check for submit.
	 *
	 * @param userId the user id
	 * @param newPass the new pass
	 * @param contractCode the contract code
	 * @return the check before change pass output
	 */
	//check passPolicyforSubmit
	CheckBeforeChangePassOutput passwordPolicyCheckForSubmit(String userId, String newPass, String contractCode);
	
	/**
	 * Check before reset password.
	 *
	 * @param userId the user id
	 * @param newPass the new pass
	 * @param reNewPass the re new pass
	 * @return the check before change pass output
	 */
	//パスワード再設定前チェック request list 445
	CheckBeforeChangePassOutput checkBeforeResetPassword(String userId, String newPass, String reNewPass);

}
