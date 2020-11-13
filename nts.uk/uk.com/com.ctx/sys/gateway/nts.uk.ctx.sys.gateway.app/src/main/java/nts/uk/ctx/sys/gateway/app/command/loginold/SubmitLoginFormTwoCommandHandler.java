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
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.SignonEmployeeInfoData;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendService;
import nts.uk.ctx.sys.gateway.app.service.login.LoginService;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.loginold.LoginStatus;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImportNew;
import nts.uk.ctx.sys.shared.dom.employee.SysEmployeeAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class SubmitLoginFormTwoCommandHandler.
 */
@Stateless
public class SubmitLoginFormTwoCommandHandler extends LoginBaseCommandHandler<SubmitLoginFormTwoCommand> {

	/** The user repository. */
	@Inject
	private UserAdapter userAdapter;

	/** The employee code setting adapter. */
	@Inject
	private LoginService employeeCodeSetting;

	/** The employee adapter. */
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	/** The service. */
	@Inject
	private LoginRecordRegistService service;
	
	@Inject
	private SystemSuspendService systemSuspendService;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected CheckChangePassDto internalHanler(CommandHandlerContext<SubmitLoginFormTwoCommand> context) {

		SubmitLoginFormTwoCommand command = context.getCommand();
		
		UserImportNew user = new UserImportNew();
		String oldPassword = null;
		EmployeeImport em = new EmployeeImport();
		String companyCode = command.getCompanyCode();
		String contractCode = command.getContractCode();
		String companyId = contractCode + "-" + companyCode;
		String employeeId = null;
		
		if (command.isSignOn()) {
			// アルゴリズム「アカウント照合」を実行する
			WindowsAccount windowAcc = this.compareAccount(context.getCommand().getRequest());
			
			//get User
			user = this.getUserAndCheckLimitTime(windowAcc);
			oldPassword = user.getPassword();
			SignonEmployeeInfoData signonData = this.getEmployeeInfoCaseSignon(windowAcc,true);
			CompanyInformationImport com = signonData.companyInformationImport;
			EmployeeImportNew emp = signonData.employeeImportNew;
			em = new EmployeeImport(com.getCompanyId(), emp.getPid(), emp.getEmployeeId(), emp.getEmployeeCode());
			companyCode = com.getCompanyCode();
			employeeId = em.getEmployeeId();
			companyId = contractCode + "-" + companyCode;
		} else {
			String employeeCode = command.getEmployeeCode();
			oldPassword = command.getPassword();
			
			// check validate input
			this.checkInput(command);
	
			//recheck contract
			if (!this.reCheckContract(contractCode, command.getContractPassword())) {
				return new CheckChangePassDto(false, null, true);
			}
			
			// Edit employee code
			employeeCode = this.employeeCodeSetting.employeeCodeEdit(employeeCode, companyId);
			
			// Get domain 社員
			em = this.getEmployee(companyId, employeeCode);
			
			//アルゴリズム「社員が削除されたかを取得」を実行する
			this.checkEmployeeDelStatus(em.getEmployeeId(), false);
			
			//imported（ゲートウェイ）「ユーザ」を取得する
			user = this.getUser(em.getPersonalId(), companyId,employeeCode);
			//2019.04.23 sou add  #107445
			//EA修正履歴No.3368
			//アルゴリズム「アカウントロックチェック」を実行する (Execute the algorithm "account lock check")
			this.checkAccoutLock(user.getLoginId(), contractCode, user.getUserId(), companyId, command.isSignOn());
			// check password
			String msgErrorId = this.compareHashPassword(user, oldPassword);
			if (msgErrorId != null){
				String remarkText = companyId + " " + employeeCode + " " + TextResource.localize(msgErrorId);
				ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value,
						LoginStatus.Fail.value, remarkText, em.getEmployeeId());
				
				// アルゴリズム「ログイン記録」を実行する１
				this.service.callLoginRecord(param);
				return new CheckChangePassDto(false, msgErrorId,false);
			} 
			
			// check time limit
			this.checkLimitTime(user, companyId,employeeCode);
			employeeId = em.getEmployeeId();
		}
		
		//ルゴリズム「エラーチェック」を実行する (Execute algorithm "error check")
		this.errorCheck2(companyId, contractCode, user.getUserId(), command.isSignOn(), employeeId);
		
		//set info to session
		context.getCommand().getRequest().changeSessionId();
		
		//ログインセッション作成 (Create login session)
        this.initSessionC(user, em, companyCode);
		
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
		
		//EA修正履歴3231
		//hoatt 2019.03.27
		Integer loginMethod = command.isSignOn() ? LoginMethod.SINGLE_SIGN_ON.value : LoginMethod.NORMAL_LOGIN.value;
		
		// アルゴリズム「ログイン記録」を実行する１
		ParamLoginRecord param = new ParamLoginRecord(companyId, loginMethod, LoginStatus.Success.value, null, employeeId);
		this.service.callLoginRecord(param);
		//hoatt 2019.05.06
		//EA修正履歴No.3372
		//アルゴリズム「ログイン後チェック」を実行する
		this.deleteLoginLog(user.getUserId());
		//アルゴリズム「ログイン後チェック」を実行する
		CheckChangePassDto checkChangePass = this.checkAfterLogin(user, oldPassword, command.isSignOn());
		checkChangePass.successMsg = systemSuspendOutput.getMsgID();
		return checkChangePass;
	}

	/**
	 * Check input.
	 *
	 * @param command the command
	 */
	private void checkInput(SubmitLoginFormTwoCommand command) {

		// check input company code
		if (StringUtil.isNullOrEmpty(command.getCompanyCode(), true)) {
			throw new BusinessException("Msg_311");
		}
		// check input employee code
		if (StringUtil.isNullOrEmpty(command.getEmployeeCode(), true)) {
			throw new BusinessException("Msg_312");
		}
//		// check input password
//		if (StringUtil.isNullOrEmpty(command.getPassword(), true)) {
//			throw new BusinessException("Msg_310");
//		}
	}

	/**
	 * Gets the employee.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @return the employee
	 */
	private EmployeeImport getEmployee(String companyId, String employeeCode) {
		Optional<EmployeeImport> em = employeeAdapter.getCurrentInfoByScd(companyId, employeeCode);
		if (em.isPresent()) {
			return em.get();
		} else {
			String remarkText = companyId + " " + employeeCode + " " + TextResource.localize("Msg_301");
			ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value,
					LoginStatus.Fail.value, remarkText, null);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			throw new BusinessException("Msg_301");
		}
	}

	/**
	 * Gets the user.
	 *
	 * @param personalId the personal id
	 * @return the user
	 */
	private UserImportNew getUser(String personalId, String companyId,String employeeCode) {
		Optional<UserImportNew> user = userAdapter.findUserByAssociateId(personalId);
		if (user.isPresent()) {
			return user.get();
		} else {
			String remarkText = companyId + " " + employeeCode + " " + TextResource.localize("Msg_301");
			ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value,
					LoginStatus.Fail.value, remarkText, null);

			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			throw new BusinessException("Msg_301");
		}
	}

	/**
	 * Check limit time.
	 *
	 * @param user the user
	 */
	private void checkLimitTime(UserImportNew user, String companyId,String employeeCode) {
		if (user.getExpirationDate().before(GeneralDate.today())) {
			String remarkText = companyId + " " + employeeCode + " " + TextResource.localize("Msg_316");
			ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value,
					LoginStatus.Fail.value, remarkText, null);
			
			// アルゴリズム「ログイン記録」を実行する１
			this.service.callLoginRecord(param);
			throw new BusinessException("Msg_316");
		}
	}
}