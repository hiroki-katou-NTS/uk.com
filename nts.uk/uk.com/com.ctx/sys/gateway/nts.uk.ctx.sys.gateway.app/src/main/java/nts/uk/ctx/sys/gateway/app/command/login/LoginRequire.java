package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.app.command.login.session.LoginAuthorizeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.CheckIfCanLogin;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.AuthenticationFailuresLog;
import nts.uk.ctx.sys.gateway.dom.outage.company.PlannedOutageByCompany;
import nts.uk.ctx.sys.gateway.dom.outage.company.PlannedOutageByCompanyRepository;
import nts.uk.ctx.sys.gateway.dom.outage.tenant.PlannedOutageByTenant;
import nts.uk.ctx.sys.gateway.dom.outage.tenant.PlannedOutageByTenantRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutDataRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockoutData;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompanyRepository;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationAdapter;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@Stateless
public class LoginRequire {
	
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;
	
	@Inject
    private TenantAuthenticationRepository tenantAuthenticationRepository;

	@Inject
	private LoginAuthorizeAdapter loginAuthorizeAdapter;
	
	@Inject
	private LoginUserContextManager loginUserContextManager;

	/**
	 * 社員に紐付かないユーザのログイン用
	 * @param require
	 */
	public <R extends LoginRequire.BaseImpl> void setup(R require) {
		
		require.setDependencies(
				companyInformationAdapter,
				tenantAuthenticationRepository,
				loginAuthorizeAdapter,
				loginUserContextManager);
	}

	public static interface CommonRequire extends
		LoginCommandHandlerBase.Require,
		CheckIfCanLogin.Require {
		
	}
	
	public static class BaseImpl implements CommonRequire {

		private CompanyInformationAdapter companyInformationAdapter;
		private TenantAuthenticationRepository tenantAuthenticationRepository;
		private LoginAuthorizeAdapter loginAuthorizeAdapter;
		private LoginUserContextManager loginUserContextManager;
		private PlannedOutageByTenantRepository plannedOutageByTenantRepository;
		private PlannedOutageByCompanyRepository plannedOutageByCompanyRepository;
		private AccountLockPolicyRepository accountLockPolicyRepository;
		private LockOutDataRepository lockOutDataRepository;

		public void setDependencies(
				CompanyInformationAdapter companyInformationAdapter,
				TenantAuthenticationRepository tenantAuthenticationRepository,
				LoginAuthorizeAdapter loginAuthorizeAdapter,
				LoginUserContextManager loginUserContextManager) {

			this.companyInformationAdapter = companyInformationAdapter;
			this.tenantAuthenticationRepository = tenantAuthenticationRepository;
			this.loginAuthorizeAdapter = loginAuthorizeAdapter;
			this.loginUserContextManager = loginUserContextManager;
		}

		@Override
		public CompanyInforImport getCompanyInforImport(String companyId) {
			return companyInformationAdapter.findComById(companyId);
		}

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepository.find(tenantCode);
		}

		@Override
		public Optional<PlannedOutageByCompany> getPlannedOutageByCompany(String companyId) {
			return plannedOutageByCompanyRepository.find(companyId);
		}

		@Override
		public Optional<PlannedOutageByTenant> getPlannedOutageByTenant(String tenantCode) {
			return plannedOutageByTenantRepository.find(tenantCode);
		}
		
		@Override
		public LoginUserRoles getLoginUserRoles(String userId) {
			return loginAuthorizeAdapter.buildUserRoles(userId);
		}

		@Override
		public Optional<AccountLockPolicy> getAccountLockPolicy(String tenantCode) {
			return accountLockPolicyRepository.getAccountLockPolicy(tenantCode);
		}

		@Override
		public List<AuthenticationFailuresLog> getFailureLog(String userId) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public List<AuthenticationFailuresLog> getFailureLog(String userId, GeneralDateTime start,
				GeneralDateTime end) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public Optional<LockoutData> getLockOutData(String userId) {
			return lockOutDataRepository.find(userId);
		}

		@Override
		public void authorizeLoginSession(IdentifiedEmployeeInfo identified) {
			val CompanyInforImport =  getCompanyInforImport(identified.getCompanyId());
			loginUserContextManager.loggedInAsEmployee(
					identified.getUserId(),
					identified.getUser().getAssociatedPersonID().get(),
					identified.getTenantCode(),
					identified.getCompanyId(),
					CompanyInforImport.getCompanyCode(),
					identified.getEmployeeId(),
					identified.getEmployee().getEmployeeCode());
			
			loginAuthorizeAdapter.authorize(
					loginUserContextManager.roleIdSetter(),
					identified.getUserId());
		}
	}
}
