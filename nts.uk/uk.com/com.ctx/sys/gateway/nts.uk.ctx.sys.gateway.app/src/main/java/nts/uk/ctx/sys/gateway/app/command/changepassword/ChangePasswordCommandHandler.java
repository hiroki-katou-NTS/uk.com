/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.changepassword;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.dom.adapter.user.CheckBeforeChangePass;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MailNoticeSetSaveCommandHandler.
 */
@Stateless
@Transactional
public class ChangePasswordCommandHandler extends CommandHandler<ChangePasswordCommand> {

	@Inject
	private UserAdapter userAdapter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ChangePasswordCommand> context) {

		String userId = AppContexts.user().userId();
		ChangePasswordCommand command = context.getCommand();

		if (command.getIsPasswordUpdate()) {
			String oldPassword = command.getOldPassword();
			String newPassword = command.getNewPassword();
			String confirmNewPassword = command.getConfirmNewPassword();
			
			if (!StringUtil.isNullOrEmpty(oldPassword, true)
					&& !StringUtil.isNullOrEmpty(newPassword, true)
					&& !StringUtil.isNullOrEmpty(confirmNewPassword, true)) {
				// Check password - Request List 383
				CheckBeforeChangePass checkResult = this.userAdapter.checkBeforeChangePassword(userId, oldPassword, newPassword, confirmNewPassword);
				if (checkResult.isError()) {
					// Throw error list
					BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();
					checkResult.getMessage().forEach(bundledBusinessExceptions::addMessage);
					if (!bundledBusinessExceptions.cloneExceptions().isEmpty()) {
						throw bundledBusinessExceptions;
					}
				} else {
					// Update password - Request List 384				
					this.userAdapter.updatePassword(userId,newPassword);
				}	
			}
		}
	}
}
