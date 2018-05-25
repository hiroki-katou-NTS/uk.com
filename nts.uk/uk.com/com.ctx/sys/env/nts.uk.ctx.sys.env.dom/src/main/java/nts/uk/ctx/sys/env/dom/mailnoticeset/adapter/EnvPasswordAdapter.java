/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.adapter;

import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.CheckChangePassOutput;

/**
 * The Interface EnvPasswordAdapter.
 */
public interface EnvPasswordAdapter {

	/**
	 * Check before change password.
	 *
	 * @param userId the user id
	 * @param currentPass the current pass
	 * @param newPass the new pass
	 * @param reNewPass the re new pass
	 * @return the check before change pass output
	 */
	CheckChangePassOutput checkBeforeChangePassword(String userId, String currentPass, String newPass, String reNewPass);

	/**
	 * Update password.
	 *
	 * @param userId the user id
	 * @param newPassword the new password
	 */
	void updatePassword(String userId,String newPassword);

}
