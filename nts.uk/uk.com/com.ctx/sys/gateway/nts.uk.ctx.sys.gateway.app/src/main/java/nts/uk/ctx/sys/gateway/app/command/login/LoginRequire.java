package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.app.command.login.session.LoginAuthorizeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.CheckIfCanLogin;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageByCompany;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageByTenant;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutData;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationAdapter;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@Stateless
public class LoginRequire {
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;
	
	@Inject
    private TenantAuthenticationRepository tenantAuthenticationRepository;
	
	@Inject
	private LoginAuthorizeAdapter authorizeAdapter;

	/**
	 * 社員に紐付かないユーザのログイン用
	 * @param require
	 */
	public <R extends LoginRequire.BaseImpl> void setup(R require) {
		
		require.setDependencies(
				userRepository,
				companyInformationAdapter,
				tenantAuthenticationRepository,
				authorizeAdapter);
	}

	public static interface CommonRequire extends
		LoginCommandHandlerBase.Require,
		CheckIfCanLogin.Require {
		
	}
	
	public static class BaseImpl implements CommonRequire {

		private UserRepository userRepository;
		private CompanyInformationAdapter companyInformationAdapter;
		private TenantAuthenticationRepository tenantAuthenticationRepository;
		private LoginAuthorizeAdapter authorizeAdapter;

		public void setDependencies(
				UserRepository userRepository,
				CompanyInformationAdapter companyInformationAdapter,
				TenantAuthenticationRepository tenantAuthenticationRepository,
				LoginAuthorizeAdapter authorizeAdapter) {

			this.userRepository = userRepository;
			this.companyInformationAdapter = companyInformationAdapter;
			this.tenantAuthenticationRepository = tenantAuthenticationRepository;
			this.authorizeAdapter = authorizeAdapter;
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

		@Override
		public LoginUserRoles getLoginUserRoles(String userId) {
			return authorizeAdapter.buildUserRoles(userId);
		}
		
	}
}
