/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.ParamLoginRecord;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.SignonEmployeeInfoData;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.service.login.LoginService;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.loginold.LoginStatus;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImportNew;

/**
 * The Class SubmitLoginFormThreeCommandHandler.
 */
@Stateless
public class SubmitLoginFormThreeCommandHandler extends LoginBaseCommandHandler<SubmitLoginFormThreeCommand> {

	/** The employee code setting adapter. */
	@Inject
	private LoginService employeeCodeSetting;

	
	@Inject
	private LoginRecordRegistService service;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected CheckChangePassDto internalHanler(CommandHandlerContext<SubmitLoginFormThreeCommand> context) {

		SubmitLoginFormThreeCommand command = context.getCommand();
		
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
			this.service.checkInput(command);
			
			//reCheck Contract
			if (!this.reCheckContract(contractCode, command.getContractPassword())) {
				return new CheckChangePassDto(false, null, true);
			}
			
			// Edit employee code
			employeeCode = this.employeeCodeSetting.employeeCodeEdit(employeeCode, companyId);
			
			// Get domain 社員
			em = this.service.getEmployee(companyId, employeeCode);
			
			// Check del state
			this.checkEmployeeDelStatus(em.getEmployeeId(), false);
					
			// Get User by PersonalId
			user = this.service.getUser(em.getPersonalId(), companyId,employeeCode);
			
			//hoatt 2019.05.07 #107445
			//EA修正履歴No.3369
			//アルゴリズム「アカウントロックチェック」を実行する (Execute the algorithm "account lock check")
			this.checkAccoutLock(user.getLoginId(), contractCode, user.getUserId(), companyId, command.isSignOn());
			
			// check password
			String msgErrorId = this.compareHashPassword(user, oldPassword);
			if (msgErrorId != null){
				return this.service.writeLogForCheckPassError(em, msgErrorId);
			} 
			
			// check time limit
			this.service.checkLimitTime(user, companyId,employeeCode);
			employeeId = em.getEmployeeId();
		}
		//ルゴリズム「エラーチェック」を実行する (Execute algorithm "error check")
		this.errorCheck2(companyId, contractCode, user.getUserId(), command.isSignOn(), employeeId);
		
		//set info to session
		command.getRequest().changeSessionId();
        
        //ログインセッション作成 (Create login session)
        this.initSessionC(user, em, companyCode);
		
		// アルゴリズム「システム利用停止の確認」を実行する
        SystemSuspendOutput systemSuspendOutput = this.service.checkSystemStop(command);
		
		if(systemSuspendOutput.isError()){
			throw new BusinessException(new RawErrorMessage(systemSuspendOutput.getMsgContent()));
		}
		//EA修正履歴3120
		//hoatt 2019.03.27
		Integer loginMethod = command.isSignOn() ? LoginMethod.SINGLE_SIGN_ON.value : LoginMethod.NORMAL_LOGIN.value;
		
		// アルゴリズム「ログイン記録」を実行する１
		ParamLoginRecord param = new ParamLoginRecord(companyId, loginMethod, LoginStatus.Success.value, null, employeeId);
		this.service.callLoginRecord(param);
		//hoatt 2019.05.06
		//EA修正履歴No.3373
		//アルゴリズム「ログイン後チェック」を実行する
		this.deleteLoginLog(user.getUserId());
		//アルゴリズム「ログイン後チェック」を実行する
		CheckChangePassDto checkChangePass = this.checkAfterLogin(user, oldPassword, command.isSignOn());
		checkChangePass.successMsg = systemSuspendOutput.getMsgID();
		return checkChangePass;
	}
}
