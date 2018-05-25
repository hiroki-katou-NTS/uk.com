/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.ac.mailnoticeset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.pub.user.ChangeUserPasswordPublisher;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforeChangePassOutput;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforePasswordPublisher;
import nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EnvPasswordAdapter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.dto.CheckChangePassOutput;

/**
 * The Class EnvPasswordAdapterImpl.
 */
@Stateless
public class EnvPasswordAdapterImpl implements EnvPasswordAdapter {

	/** The check before password publisher. */
	@Inject
	private CheckBeforePasswordPublisher checkBeforePasswordPublisher;

	/** The change user password publisher. */
	@Inject
	private ChangeUserPasswordPublisher changeUserPasswordPublisher;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EnvPasswordAdapter#
	 * checkBeforeChangePassword(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public CheckChangePassOutput checkBeforeChangePassword(String userId, String currentPass, String newPass,
			String reNewPass) {
		CheckBeforeChangePassOutput result = this.checkBeforePasswordPublisher.checkBeforeChangePassword(userId,
				currentPass, newPass, reNewPass);
		return new CheckChangePassOutput(result.isError(), result.getMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.adapter.EnvPasswordAdapter#
	 * updatePassword(java.lang.String)
	 */
	@Override
	public void updatePassword(String userId, String newPassword) {
		changeUserPasswordPublisher.changePass(userId, newPassword);
	}

}
