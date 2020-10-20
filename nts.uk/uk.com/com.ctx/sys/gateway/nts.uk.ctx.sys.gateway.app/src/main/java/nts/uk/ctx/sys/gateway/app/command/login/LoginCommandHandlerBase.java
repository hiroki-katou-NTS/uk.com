package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.adapter.CompanyInformationAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleFromUserIdAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleFromUserIdAdapter.RoleInfoImport;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.FindTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
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
		S extends LoginCommandHandlerBase.LoginState<C>,
		R >
		extends CommandHandlerWithResult<C, R> {
	
	@Inject
	private TenantAuthenticationRepository tenantAuthenticationRepository;
	
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;
	
	@Inject
	private RoleFromUserIdAdapter roleFromUserIdAdapter;
	
	@Inject
	private SessionLowLayer sessionLowLayer;
	
	@Inject
	private LoginUserContextManager manager;

	@Override
	protected R handle(CommandHandlerContext<C> context) {
		
		C command = context.getCommand();
		

		/* テナントロケーター処理 */
		
		

		// テナント認証
		FindTenant.Require require = EmbedStopwatch.embed(new RequireImpl());
//		boolean successTenantAuth = FindTenant.byTenantCode(require, command.getTenantCode(), GeneralDate.today())
//				.map(t -> t.verify(command.getTenantPasswordPlainText()))
//				.orElse(false);
		
		
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
		
		S state = processBeforeLogin(command);
		
		if (state.isSuccess()) {
			initSession(state);
			return processSuccess(state);
		} else {
			return processFailure(state);
		}
		
	}
	
	private void initSession(S state) {
		
		sessionLowLayer.loggedIn();
		
		/* 社員IDとかロールとか、セッションに持たせる情報をセット */
		

		
		val employee = state.getEmployee();
		
		val user = state.getUser();
		
		val company = companyInformationAdapter.findById(employee.getCompanyId());
		
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
		setRoleInfo(user.getUserID(), company.getCompanyId());
	}
	
	private void setRoleInfo(String userId, String companyId) {
        Optional<RoleInfoImport> employmentRole = this.getRoleInfo(userId, companyId, RoleType.EMPLOYMENT);
        Optional<RoleInfoImport> salaryRole = this.getRoleInfo(userId, companyId, RoleType.SALARY);
        Optional<RoleInfoImport> humanResourceRole = this.getRoleInfo(userId, companyId, RoleType.HUMAN_RESOURCE);
        Optional<RoleInfoImport> personalInfoRole = this.getRoleInfo(userId, companyId, RoleType.PERSONAL_INFO);
        String officeHelperRoleId = this.getRoleId(userId, RoleType.OFFICE_HELPER);
        String groupCompanyManagerRoleId = this.getRoleId(userId, RoleType.GROUP_COMAPNY_MANAGER);
		String companyManagerRoleId = this.getRoleId(userId, RoleType.COMPANY_MANAGER);
		String systemManagerRoleId = this.getRoleId(userId, RoleType.SYSTEM_MANAGER);
		
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
    protected Optional<RoleInfoImport> getRoleInfo(String userId, String companyId, RoleType roleType) {
        return roleFromUserIdAdapter.getRoleInfoFromUser(userId, roleType.value, GeneralDate.today(), companyId);
    }
    
    // ロールIDを取得
	protected String getRoleId(String userId, RoleType roleType) {
		String roleId = roleFromUserIdAdapter.getRoleFromUser(userId, roleType.value, GeneralDate.today());
		if (StringUtils.isEmpty(roleId)) {
			return null;
		}
		return roleId;
	}


	
	protected abstract R getResultOnFailTenantAuth();
	
	/**
	 * ログイン（認証）処理本体
	 * @param command
	 * @return
	 */
	protected abstract S processBeforeLogin(C command);
	
	/**
	 * ログイン成功時の処理
	 * @param state
	 * @return
	 */
	protected abstract R processSuccess(S state);
	
	/**
	 * ログイン失敗時の処理
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
	
	public static interface LoginState<R> {
		
		boolean isSuccess();
		
		EmployeeImport getEmployee();
		
		User getUser();
	}
	
	public class RequireImpl implements FindTenant.Require{

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode, GeneralDate date) {
			return tenantAuthenticationRepository.find(tenantCode, date);
		}

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepository.find(tenantCode);
		}
	}
}