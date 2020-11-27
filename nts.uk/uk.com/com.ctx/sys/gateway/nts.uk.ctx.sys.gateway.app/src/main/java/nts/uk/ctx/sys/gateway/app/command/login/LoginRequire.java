package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.app.command.login.session.LoginAuthorizeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.CheckIfCanLogin;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageByCompany;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageByTenant;
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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<PlannedOutageByTenant> getPlannedOutageByTenant(String tenantCode) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public LoginUserRoles getLoginUserRoles(String userId) {
			return loginAuthorizeAdapter.buildUserRoles(userId);
		}

		@Override
		public void authorizeLoginSession(IdentifiedEmployeeInfo identified) {
			loginAuthorizeAdapter.authorize(
					loginUserContextManager.roleIdSetter(),
					identified.getUser().getUserID());
		}
		
	}
}
