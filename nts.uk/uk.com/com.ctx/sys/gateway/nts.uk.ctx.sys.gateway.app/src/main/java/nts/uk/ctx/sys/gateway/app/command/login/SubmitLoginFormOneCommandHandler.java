/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImport;

/**
 * The Class SubmitLoginFormOneCommandHandler.
 */
@Stateless
public class SubmitLoginFormOneCommandHandler extends LoginBaseCommandHandler<SubmitLoginFormOneCommand> {

	/** The user repository. */
	@Inject
	private UserAdapter userAdapter;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected String internalHanler(CommandHandlerContext<SubmitLoginFormOneCommand> context) {
		
		SubmitLoginFormOneCommand command = context.getCommand();
		if (command.isSignOn()) {
			//アルゴリズム「アカウント照合」を実行する
			this.compareAccount();
		} else {
			String loginId = command.getLoginId();
			String password = command.getPassword();
			// check validate input
			this.checkInput(command);
	
			this.reCheckContract(command.getContractCode(), command.getContractPassword());
			
			// find user by login id
			Optional<UserImport> user = userAdapter.findUserByContractAndLoginId(command.getContractCode(), loginId);
			if (!user.isPresent()) {
				throw new BusinessException("Msg_301");
			}
	
			// check password
			String msgErrorId = this.compareHashPassword(user.get(), password);
			if (!StringUtil.isNullOrEmpty(msgErrorId, true)){
				return msgErrorId;
			} 
	
			// check time limit
			this.checkLimitTime(user);
			
			//set info to session
			HttpSession session = context.getCommand().getRequest().getSession(false);
			if (session != null) session.invalidate();
			context.getCommand().getRequest().getSession(true);
			this.initSession(user.get());
		}
		return null;
	}

	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SubmitLoginFormOneCommand command) {
		//check input loginId
		if (command.getLoginId().trim().isEmpty() || command.getLoginId() == null) {
			throw new BusinessException("Msg_309");
		}
		//check input password
		if (StringUtil.isNullOrEmpty(command.getPassword(), true)) {
			throw new BusinessException("Msg_310");
		}
	}
	
	/**
	 * Check limit time.
	 *
	 * @param user the user
	 */
	private void checkLimitTime(Optional<UserImport> user) {
		if (user.get().getExpirationDate().before(GeneralDate.today())) {
			throw new BusinessException("Msg_316");
		}
	}
}
