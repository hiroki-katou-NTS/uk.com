/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.login.dto.ParamLoginRecord;
import nts.uk.ctx.sys.gateway.app.command.login.dto.SignonEmployeeInfoData;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.service.login.LoginService;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.login.LoginStatus;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImportNew;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;

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
		
        SystemSuspendOutput systemSuspendOutput = this.service.checkSystemStop(command);
		
		//アルゴリズム「ログイン記録」を実行する
		CheckChangePassDto passChecked = this.checkAfterLogin(user, oldPassword);
		if (passChecked.showChangePass){
			return passChecked;
		}
		
		Integer loginMethod = LoginMethod.NORMAL_LOGIN.value;
		
		if (command.isSignOn()) {
			loginMethod = LoginMethod.SINGLE_SIGN_ON.value;
		}
		
		// アルゴリズム「ログイン記録」を実行する１
		ParamLoginRecord param = new ParamLoginRecord(companyId, loginMethod, LoginStatus.Success.value, null, employeeId);
		this.service.callLoginRecord(param);
		
		CheckChangePassDto checkChangePassDto = new CheckChangePassDto(false, null,false);
		checkChangePassDto.successMsg = systemSuspendOutput.getMsgID();
		return checkChangePassDto;
	}
}
