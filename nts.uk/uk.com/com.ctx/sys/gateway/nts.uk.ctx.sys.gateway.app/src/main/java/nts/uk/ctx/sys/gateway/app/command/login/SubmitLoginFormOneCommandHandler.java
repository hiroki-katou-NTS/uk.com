/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;

/**
 * The Class SubmitLoginFormOneCommandHandler.
 */
@Stateless
public class SubmitLoginFormOneCommandHandler extends LoginBaseCommandHandler<SubmitLoginFormOneCommand> {

	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected CheckChangePassDto internalHanler(CommandHandlerContext<SubmitLoginFormOneCommand> context) {
		
		SubmitLoginFormOneCommand command = context.getCommand();
		
		UserImportNew user = new UserImportNew();
		String oldPassword = null;
		
		if (command.isSignOn()) {
			//アルゴリズム「アカウント照合」を実行する
			WindowsAccount windowAcc = this.compareAccount(context.getCommand().getRequest());
			
			//get User
			user = this.getUserAndCheckLimitTime(windowAcc);
			oldPassword = user.getPassword();
		} else {
			String loginId = command.getLoginId();
			oldPassword = command.getPassword();
			// check validate input
			this.checkInput(command);
	
			this.reCheckContract(command.getContractCode(), command.getContractPassword());
			
			// find user by login id
			Optional<UserImportNew> userOp = userAdapter.findUserByContractAndLoginIdNew(command.getContractCode(), loginId);
			if (!userOp.isPresent()) {
				throw new BusinessException("Msg_301");
			}
			
			// check password
			String msgErrorId = this.compareHashPassword(userOp.get(), oldPassword);
			if (!StringUtil.isNullOrEmpty(msgErrorId, true)){
				return new CheckChangePassDto(false, msgErrorId);
			}
	
			// check time limit
			this.checkLimitTime(userOp);
			
			user = userOp.get();
		}
		//アルゴリズム「エラーチェック（形式１）」を実行する
		this.errorCheck(user.getUserId(), RoleType.COMPANY_MANAGER.value, command.getContractCode());
		
		//ログインセッション作成 (set info to session)
		context.getCommand().getRequest().changeSessionId();
		this.initSession(user);
		
		//アルゴリズム「ログイン記録」を実行する
		if (!this.checkAfterLogin(user, oldPassword)){
			return new CheckChangePassDto(true, null);
		}
		
		return new CheckChangePassDto(false, null);
	}

	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SubmitLoginFormOneCommand command) {
		//check input loginId
		if (command.getLoginId() == null || command.getLoginId().trim().isEmpty()) {
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
	private void checkLimitTime(Optional<UserImportNew> user) {
		if (user.get().getExpirationDate().before(GeneralDate.today())) {
			throw new BusinessException("Msg_316");
		}
	}
}
