package nts.uk.ctx.sys.gateway.app.command.login;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.login.dto.ParamLoginRecord;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.service.login.LoginService;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.login.LoginStatus;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;

@Stateless
public abstract class MobileLoginCommonHandler extends LoginBaseCommandHandler<BasicLoginCommand> {


	/** The employee code setting adapter. */
	@Inject
	private LoginService employeeCodeSetting;
	
	@Inject
	private LoginRecordRegistService service;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected CheckChangePassDto internalHanler(CommandHandlerContext<BasicLoginCommand> context) {

		BasicLoginCommand command = context.getCommand();
		// check validate input
		this.service.checkInput(command);
		
		String companyId = this.employeeCodeSetting.comanyId(command.getContractCode(), command.getCompanyCode());
		
		//reCheck Contract
		if (!this.reCheckContract(command.getContractCode(), command.getContractPassword())) {
			return new CheckChangePassDto(false, null, true);
		}
		
		// Edit employee code
		String employeeCode = this.employeeCodeSetting.employeeCodeEdit(command.getEmployeeCode(), companyId);
		
		// Get domain 社員
		EmployeeImport em = this.service.getEmployee(companyId, employeeCode);
		
		// Check del state
		this.checkEmployeeDelStatus(em.getEmployeeId(), false);
				
		// Get User by PersonalId
		UserImportNew user = this.service.getUser(em.getPersonalId(), companyId,employeeCode);
		
		// check password
		String msgErrorId = this.compareHashPassword(user, command.getPassword());
		if (msgErrorId != null){
			return this.service.writeLogForCheckPassError(em, msgErrorId);
		} 
		
		// check time limit
		this.service.checkLimitTime(user, companyId,employeeCode);
		
		
		//ルゴリズム「エラーチェック」を実行する (Execute algorithm "error check")
		this.errorCheck2(companyId, command.getContractCode(), user.getUserId(), false, em.getEmployeeId());
		
		//アルゴリズム「ログイン記録」を実行する
		CheckChangePassDto passChecked = this.checkAfterLogin(user, command.getPassword());
		if (passChecked.showChangePass && this.needShowChangePass()){
			return passChecked;
		}
		
		//set info to session
		command.getRequest().changeSessionId();
        
        //ログインセッション作成 (Create login session)
        this.initSessionC(user, em, command.getCompanyCode());
		
		SystemSuspendOutput systemSuspendOutput = this.service.checkSystemStop(command);
		
		// アルゴリズム「ログイン記録」を実行する１
		ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value, LoginStatus.Success.value, null, em.getEmployeeId());
		this.service.callLoginRecord(param);
		
		CheckChangePassDto checkChangePassDto = new CheckChangePassDto(false, null,false);
		checkChangePassDto.successMsg = systemSuspendOutput.getMsgID();
		return checkChangePassDto;
	}

	protected abstract boolean needShowChangePass();
	
}