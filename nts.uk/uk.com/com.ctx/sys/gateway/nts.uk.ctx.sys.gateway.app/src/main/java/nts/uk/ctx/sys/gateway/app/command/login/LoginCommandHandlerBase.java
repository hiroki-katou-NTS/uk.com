package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleFromUserIdAdapter.RoleInfoImport;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.FindTenant;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;
import nts.uk.shr.com.context.loginuser.SessionLowLayer;

/**
 * TenantLocatorを想定したログイン処理の基底クラス
 *
 * @param <C> Command
 * @param <R> Result
 */
@Stateless
public abstract class LoginCommandHandlerBase<
		C extends LoginCommandHandlerBase.TenantAuth,
		S extends LoginCommandHandlerBase.LoginState,
		R ,
		Q extends LoginCommandHandlerBase.Require>
		extends CommandHandlerWithResult<C, R> {
	
	@Inject
	private SessionLowLayer sessionLowLayer;
	
	@Inject
	private LoginUserContextManager manager;

	@Override
	protected R handle(CommandHandlerContext<C> context) {
		
		C command = context.getCommand();
		
		/* テナントロケーター処理 */
		
		// テナント認証
		Q require = getRequire();
		val opTenant = FindTenant.byTenantCode(require, command.getTenantCode(), GeneralDate.today());
		if(!opTenant.isPresent()) {
			return getResultOnFailTenantAuth();
		}
		val tenant = opTenant.get();
		
		val passwordVerify = tenant.verify(command.getTenantPasswordPlainText());		
		val available = tenant.isAvailableAt(GeneralDate.today());
		
		if(!passwordVerify || !available) {
			// テナント認証失敗
			/* テナントロケーターのdisconnect処理 */
			return getResultOnFailTenantAuth();
		}
		
		S state = processBeforeLogin(require, command);
		
		if (state.isSuccess()) {
			initSession(require, state);
			return processSuccess(state);
		} else {
			return processFailure(state);
		}
		/* ログインログ */
	}
	
	private void initSession(Q require, S state) {
		
		sessionLowLayer.loggedIn();
		
		val employee = state.getEmployee();
		
		val user = state.getUser();
		
		val company = require.getCompanyInformationImport(employee.getCompanyId());
		
		// 会社、社員、ユーザの情報をセット
		manager.loggedInAsEmployee(
				user.getUserID(), 
				user.getAssociatedPersonID().isPresent() ? user.getAssociatedPersonID().get() : null, 
				user.getContractCode().toString(), 
				company.getCompanyId(), 
				company.getCompanyCode(), 
				employee.getEmployeeId(), 
				employee.getEmployeeCode());
		
		// 権限情報をセット
		setRoleInfo(require, user.getUserID(), company.getCompanyId());
	}
	
	private void setRoleInfo(Q require, String userId, String companyId) {
        Optional<RoleInfoImport> employmentRole = this.getRoleInfo(require, userId, companyId, RoleType.EMPLOYMENT);
        Optional<RoleInfoImport> salaryRole = this.getRoleInfo(require, userId, companyId, RoleType.SALARY);
        Optional<RoleInfoImport> humanResourceRole = this.getRoleInfo(require, userId, companyId, RoleType.HUMAN_RESOURCE);
        Optional<RoleInfoImport> personalInfoRole = this.getRoleInfo(require, userId, companyId, RoleType.PERSONAL_INFO);
        String officeHelperRoleId = this.getRoleId(require, userId, RoleType.OFFICE_HELPER);
        String groupCompanyManagerRoleId = this.getRoleId(require, userId, RoleType.GROUP_COMAPNY_MANAGER);
		String companyManagerRoleId = this.getRoleId(require, userId, RoleType.COMPANY_MANAGER);
		String systemManagerRoleId = this.getRoleId(require, userId, RoleType.SYSTEM_MANAGER);
		
		// 就業
        if (employmentRole.isPresent()) {
            manager.roleIdSetter().forAttendance(employmentRole.get().getRoleId());
            manager.roleIdSetter().isInChargeAttendance(employmentRole.get().isInCharge());            
		}
		// 給与
        if (salaryRole.isPresent()) {
            manager.roleIdSetter().forPayroll(salaryRole.get().getRoleId());
            manager.roleIdSetter().isInChargePayroll(salaryRole.get().isInCharge());    
		}
		// 人事
        if (humanResourceRole.isPresent()) {
            manager.roleIdSetter().forPersonnel(humanResourceRole.get().getRoleId());
            manager.roleIdSetter().isInChargePersonnel(humanResourceRole.get().isInCharge());    
        }
        // 個人情報
        if (personalInfoRole.isPresent()) {
            manager.roleIdSetter().forPersonalInfo(personalInfoRole.get().getRoleId());
            manager.roleIdSetter().isInChargePersonalInfo(personalInfoRole.get().isInCharge());    
		}
		// オフィスヘルパー
		if (officeHelperRoleId != null) {
			manager.roleIdSetter().forOfficeHelper(officeHelperRoleId);
		}
		// 会計
		// マイナンバー
		// グループ会社管理
		if (groupCompanyManagerRoleId != null) {
			manager.roleIdSetter().forGroupCompaniesAdmin(groupCompanyManagerRoleId);
		}
		// 会社管理者
		if (companyManagerRoleId != null) {
			manager.roleIdSetter().forCompanyAdmin(companyManagerRoleId);
		}
		// システム管理者
		if (systemManagerRoleId != null) {
			manager.roleIdSetter().forSystemAdmin(systemManagerRoleId);
		}
	}
	
	// ロール情報を取得
    protected Optional<RoleInfoImport> getRoleInfo(Q require, String userId, String companyId, RoleType roleType) {
        return require.getRoleInfoImport(userId, roleType.value, GeneralDate.today(), companyId);
    }
    
    // ロールIDを取得
	protected String getRoleId(Q require, String userId, RoleType roleType) {
		String roleId = require.getRoleId(userId, roleType.value, GeneralDate.today());
		if (StringUtils.isEmpty(roleId)) {
			return null;
		}
		return roleId;
	}

	/**
	 * テナント認証失敗時の処理
	 * @param 
	 * @return
	 */
	protected abstract R getResultOnFailTenantAuth();
	
	/**
	 * 認証処理本体
	 * @param require
	 * @param command
	 * @return
	 */
	protected abstract S processBeforeLogin(Q require, C command);
	
	/**
	 * 認証成功時の処理
	 * @param state
	 * @return
	 */
	protected abstract R processSuccess(S state);
	
	/**
	 * 認証失敗時の処理
	 * @param state
	 * @return
	 */
	protected abstract R processFailure(S state);
	
	public static interface TenantAuth {
		
		/** テナントコード */
		String getTenantCode();
		
		/** テナント認証パスワードの平文 */
		String getTenantPasswordPlainText();
		
	}
	
	public static interface LoginState {
		
		boolean isSuccess();
		
		EmployeeImport getEmployee();
		
		User getUser();
	}
	
	protected abstract Q getRequire();
	
	public static interface Require extends FindTenant.Require{
		CompanyInformationImport getCompanyInformationImport(String companyId);
		
		Optional<RoleInfoImport> getRoleInfoImport(String userId, int roleType, GeneralDate baseDate, String comId);
		
		String getRoleId(String userId,Integer roleType,GeneralDate baseDate);
		
	}	
}



// privateかpublicか整理しておく