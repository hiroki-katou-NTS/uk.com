package nts.uk.ctx.sys.gateway.app.command.loginold;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.common.base.Objects;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.ParamLoginRecord;
import nts.uk.ctx.sys.gateway.app.command.systemsuspend.SystemSuspendOutput;
import nts.uk.ctx.sys.gateway.app.service.login.LoginService;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.loginold.LoginStatus;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@Stateless
public abstract class MobileLoginCommonHandler extends LoginBaseCommandHandler<MobileLoginCommand> {


	/** The employee code setting adapter. */
	@Inject
	private LoginService employeeCodeSetting;
	
	@Inject
	private LoginRecordRegistService service;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected CheckChangePassDto internalHanler(CommandHandlerContext<MobileLoginCommand> context) {

		MobileLoginCommand command = context.getCommand();
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
		UserImportNew user = this.service.getUser(em.getPersonalId(), companyId, employeeCode);

        LoginUserRoles roles = this.checkRole(user.getUserId(), companyId);
		SystemSuspendOutput systemSuspendOutput = this.service.checkSystemStop(command, roles);

		this.checkAccoutLock(user.getLoginId(), command.getContractCode(), user.getUserId(), companyId, command.isSignOn());
		
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
		CheckChangePassDto passChecked = this.checkAfterLogin(user, command.getPassword(), false);
		passChecked.successMsg = systemSuspendOutput.getMsgID();
		
		if (passChecked.showChangePass && this.needShowChangePass()){
			return passChecked;
		}
		
		if(!(!command.isLoginDirect() && Objects.equal(passChecked.msgErrorId, "Msg_1517")) || command.isLoginDirect()){
			
			//set info to session
			command.getRequest().changeSessionId();
	        
	        //ログインセッション作成 (Create login session)
	        this.initSessionC(user, em, command.getCompanyCode(), roles);
			
			// アルゴリズム「ログイン記録」を実行する１
			ParamLoginRecord param = new ParamLoginRecord(companyId, LoginMethod.NORMAL_LOGIN.value, LoginStatus.Success.value, null, em.getEmployeeId());
			this.service.callLoginRecord(param);
			
			//hoatt 2019.05.06
			//EA修正履歴No.3373
			//アルゴリズム「ログイン後チェック」を実行する
			this.deleteLoginLog(user.getUserId());
		}
		
		return passChecked;
	}

	protected abstract boolean needShowChangePass();
	
}