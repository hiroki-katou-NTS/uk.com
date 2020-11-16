package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.CheckIfCanLogin;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageByCompany;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageByTenant;
import nts.uk.ctx.sys.gateway.dom.role.RoleFromUserIdAdapter;
import nts.uk.ctx.sys.gateway.dom.role.RoleInfoImport;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutData;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationAdapter;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;

@Stateless
public class LoginRequire {
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;
	
	@Inject
    private TenantAuthenticationRepository tenantAuthenticationRepository;
	
	@Inject
    private RoleFromUserIdAdapter roleFromUserIdAdapter;

	/**
	 * 社員に紐付かないユーザのログイン用
	 * @param require
	 */
	public <R extends LoginRequire.BaseImpl> void setup(R require) {
		
		require.setDependencies(
				userRepository,
				companyInformationAdapter,
				tenantAuthenticationRepository,
				roleFromUserIdAdapter);
	}

	public static interface CommonRequire extends
		LoginCommandHandlerBase.Require,
		CheckIfCanLogin.Require {
		
	}
	
	public static class BaseImpl implements CommonRequire {

		protected UserRepository userRepository;
		protected CompanyInformationAdapter companyInformationAdapter;
		protected TenantAuthenticationRepository tenantAuthenticationRepository;
		protected RoleFromUserIdAdapter roleFromUserIdAdapter;

		public void setDependencies(
				UserRepository userRepository,
				CompanyInformationAdapter companyInformationAdapter,
				TenantAuthenticationRepository tenantAuthenticationRepository,
				RoleFromUserIdAdapter roleFromUserIdAdapter) {

			this.userRepository = userRepository;
			this.companyInformationAdapter = companyInformationAdapter;
			this.tenantAuthenticationRepository = tenantAuthenticationRepository;
			this.roleFromUserIdAdapter = roleFromUserIdAdapter;
		}

		@Override
		public CompanyInforImport getCompanyInforImport(String companyId) {
			return companyInformationAdapter.findComById(companyId);
		}

		@Override
		public Optional<RoleInfoImport> getRoleInfoImport(
				String userId, int roleType, GeneralDate baseDate, String comId) {
			return roleFromUserIdAdapter.getRoleInfoFromUser(userId, roleType, baseDate, comId);
		}

		@Override
		public String getRoleId(String userId, Integer roleType, GeneralDate baseDate) {
			return roleFromUserIdAdapter.getRoleFromUser(userId, roleType, baseDate);
		}

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepository.find(tenantCode);
		}

		@Override
		public Optional<PlannedOutageByCompany> getPlannedOutageByCompany(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<PlannedOutageByTenant> getPlannedOutageByTenant(String tenantCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<AccountLockPolicy> getAccountLockPolicy(String tenantCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<LockOutData> getLockOutData(String userId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<String> getPersonalIdByEmployeeId(String employeeId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<User> getUser(String personalId) {
			return userRepository.getByAssociatedPersonId(personalId);
		}
		
	}
}
