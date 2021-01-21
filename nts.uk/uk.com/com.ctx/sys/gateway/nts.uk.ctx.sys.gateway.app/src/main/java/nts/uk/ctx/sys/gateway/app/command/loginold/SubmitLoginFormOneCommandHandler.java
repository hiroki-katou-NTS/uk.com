/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.ParamLoginRecord;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendService;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.loginold.LoginStatus;
import nts.uk.ctx.sys.gateway.dom.role.RoleType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LoginMethod;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class SubmitLoginFormOneCommandHandler.
 */
@Stateless
public class SubmitLoginFormOneCommandHandler extends LoginBaseCommandHandler<SubmitLoginFormOneCommand> {

	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;
	
	/** The service. */
	@Inject
	private LoginRecordRegistService service;
	
	@Inject
	private SystemSuspendService systemSuspendService;
	
	/* 
	 * ログイン（形式１）
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

			if (!this.reCheckContract(command.getContractCode(), command.getContractPassword())) {
				return new CheckChangePassDto(false, null, true);
			}
			
			// find user by login id
			Optional<UserImportNew> userOp = userAdapter.findUserByContractAndLoginIdNew(command.getContractCode(), loginId);
			if (!userOp.isPresent()) {
				String remarkText = loginId + " " + TextResource.localize("Msg_301");
				ParamLoginRecord param = new ParamLoginRecord(" ", LoginMethod.NORMAL_LOGIN.value,
						LoginStatus.Fail.value, remarkText, null);
				
				// アルゴリズム「ログイン記録」を実行する１
				this.service.callLoginRecord(param);
				
				throw new BusinessException("Msg_301");
			}
			//hoatt 2019.05.07 #107445
			//EA修正履歴No.3367
			user = userOp.get();
			//アルゴリズム「アカウントロックチェック」を実行する (check account lock)
			this.checkAccoutLock(loginId, command.getContractCode(), user.getUserId(), "", command.isSignOn());
			// check password
			String msgErrorId = this.compareHashPassword(userOp.get(), oldPassword);
			if (!StringUtil.isNullOrEmpty(msgErrorId, true)){
				String remarkText = loginId + " " + TextResource.localize(msgErrorId);
				ParamLoginRecord param = new ParamLoginRecord(" ", LoginMethod.NORMAL_LOGIN.value,
						LoginStatus.Fail.value, remarkText, null);
				
				// アルゴリズム「ログイン記録」を実行する１
				this.service.callLoginRecord(param);
				
				return new CheckChangePassDto(false, msgErrorId,false);
			}
	
			// check time limit
			this.checkLimitTime(userOp,loginId);
			
			user = userOp.get();
		}
		//アルゴリズム「エラーチェック（形式１）」を実行する
		this.errorCheck(command.getLoginId(),user.getUserId(), RoleType.COMPANY_MANAGER.value, command.getContractCode(), command.isSignOn());
		
		//ログインセッション作成 (set info to session)
		context.getCommand().getRequest().changeSessionId();
		this.initSession(user);
		
		// アルゴリズム「システム利用停止の確認」を実行する
		String programID = AppContexts.programId().substring(0, 6);
		String screenID = AppContexts.programId().substring(6);
		SystemSuspendOutput systemSuspendOutput = systemSuspendService.confirmSystemSuspend(
				AppContexts.user().contractCode(), 
				AppContexts.user().companyCode(),
				command.isSignOn() ? 1 : 0,
				programID,
				screenID);
		if(systemSuspendOutput.isError()){
			throw new BusinessException(new RawErrorMessage(systemSuspendOutput.getMsgContent()));
		}
		
		//EA修正履歴3230
		//hoatt 2019.03.27
		Integer loginMethod = command.isSignOn() ? LoginMethod.SINGLE_SIGN_ON.value : LoginMethod.NORMAL_LOGIN.value;
		// アルゴリズム「ログイン記録」を実行する１
		ParamLoginRecord param = new ParamLoginRecord(" ", loginMethod, LoginStatus.Success.value, null, null);
		this.service.callLoginRecord(param);
		//hoatt 2019.05.06
		//EA修正履歴No.3371
		//アルゴリズム「ログイン後チェック」を実行する
		this.deleteLoginLog(user.getUserId());
		CheckChangePassDto checkChangePass = this.checkAfterLogin(user, oldPassword, command.isSignOn());
		checkChangePass.successMsg = systemSuspendOutput.getMsgID();
		return checkChangePass;
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
//		//check input password
//		if (StringUtil.isNullOrEmpty(command.getPassword(), true)) {
//			throw new BusinessException("Msg_310");
//		}
	}
	
	/**
	 * Check limit time.
	 *
	 * @param user the user
	 */
	private void checkLimitTime(Optional<UserImportNew> user,String loginId) {
		if (user.get().getExpirationDate().before(GeneralDate.today())) {
			String remarkText = loginId + " " + TextResource.localize("Msg_316");
			ParamLoginRecord param = new ParamLoginRecord(" ", LoginMethod.NORMAL_LOGIN.value, LoginStatus.Fail.value,
					remarkText, null);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			
			throw new BusinessException("Msg_316");
		}
	}
}