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
import nts.uk.shr.com.url.RegisterEmbededURL;

@Stateless
@Transactional
public class ForgotPasswordCommandHandler extends CommandHandler<ForgotPasswordCommand> {
	
	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;
	
	@Inject
	private RegisterEmbededURL registerEmbededURL;

	@Override
	protected void handle(CommandHandlerContext<ForgotPasswordCommand> context) {

		
		ForgotPasswordCommand command = context.getCommand();
		
		String userId = command.getUserId();
		String newPassword = command.getNewPassword();
		String confirmNewPassword = command.getConfirmNewPassword();
		
		//check PassLimitExpire
		this.registerEmbededURL.checkPassLimitExpire(command.getEmbeddedId());
		
		if (!StringUtil.isNullOrEmpty(newPassword, true)
				&& !StringUtil.isNullOrEmpty(confirmNewPassword, true)) {
			// Check password - Request List 445
			CheckBeforeChangePass checkResult = this.userAdapter.checkBeforeResetPassword(userId, newPassword, confirmNewPassword);
			if (checkResult.isError()) {
				// Throw error list
				BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();
				checkResult.getMessage().forEach(item -> {
					// get messageId
					String msgId = item.getMessage();
					String param = item.getParam();
					if (param != null) {
						bundledBusinessExceptions.addMessage(msgId, param);
					} else {
						bundledBusinessExceptions.addMessage(msgId);
					}

				});
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
