package nts.uk.ctx.sys.gateway.app.command.login.password;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Value;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.CheckIfCanLogin;
import nts.uk.ctx.sys.gateway.dom.login.password.AuthenticateEmployeePassword;
import nts.uk.ctx.sys.gateway.dom.login.password.AuthenticationFailuresLog;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageByCompany;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageByTenant;
import nts.uk.ctx.sys.gateway.dom.role.RoleFromUserIdAdapter;
import nts.uk.ctx.sys.gateway.dom.role.RoleFromUserIdAdapter.RoleInfoImport;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutData;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationAdapter;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationImport;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;
import nts.uk.ctx.sys.shared.dom.employee.SysEmployeeAdapter;
import nts.uk.ctx.sys.shared.dom.user.FindUser;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PasswordAuthenticateCommandHandler extends LoginCommandHandlerBase<
															PasswordAuthenticateCommand, 
															PasswordAuthenticateCommandHandler.AuthenState,
															PasswordAuthenticateCommandHandler.AuthorResult,
															CheckChangePassDto, 
															PasswordAuthenticateCommandHandler.Require> {
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;
	
	@Inject
    private TenantAuthenticationRepository tenantAuthenticationRepository;
	
	@Inject
    private RoleFromUserIdAdapter roleFromUserIdAdapter;
	
	// テナント認証失敗時の処理
	@Override
	protected CheckChangePassDto getResultOnFailTenantAuth() {
		return CheckChangePassDto.failedToAuthTenant();
	}
	
	// 認証処理本体
	@Override
	protected AuthenState authenticate(Require require, PasswordAuthenticateCommand command) {
		
		// 入力チェック
		checkInput(command);

		String companyId = companyInformationAdapter.createCompanyId(command.getTenantCode(), command.getCompanyCode());
		String employeeCode = command.getEmployeeCode();
		String password = command.getPassword();
		
		
		// パスワード認証
		val authenticationResult = AuthenticateEmployeePassword.authenticate(require, companyId, employeeCode, password);
		if(!authenticationResult.isSuccess()) {
			// パスワード認証失敗
			return AuthenState.failed(authenticationResult.getAtomTask());
		}
		
		// パスワード認証成功
		Optional<EmployeeImport> optEmployee = employeeAdapter.getCurrentInfoByScd(companyId, employeeCode);
		Optional<User> optUser = FindUser.byEmployeeCode(require, companyId, employeeCode);
		if(!optEmployee.isPresent() || !optUser.isPresent()) {
			throw new BusinessException("Msg_318");
		}
		
		return AuthenState.success(optEmployee.get(), optUser.get());
	}

	// 認証成功時の処理
	@Override
	protected AuthorResult processSuccess(Require require, AuthenState state) {
		
		return AuthorResult.of(CheckChangePassDto.successToAuthPassword());
	}

	// 認証失敗時の処理
	@Override
	protected AuthorResult processFailure(Require require, AuthenState state) {
		return AuthorResult.of(CheckChangePassDto.failedToAuthPassword());
	}
	
	// 入力チェック
	public void checkInput(PasswordAuthenticateCommand command) {
		// 社員コードが未入力でないかチェック
		if (StringUtil.isNullOrEmpty(command.getEmployeeCode(), true)) {
			throw new BusinessException("Msg_312");
		}
		// パスワードが未入力でないかチェック
		if (StringUtil.isNullOrEmpty(command.getPassword(), true)) {
			throw new BusinessException("Msg_310");
		}
	}

	@Value
	static class AuthenState implements LoginCommandHandlerBase.AuthenticationResult {
			
		private boolean isSuccess;
		private EmployeeImport employee;
		private User user;
		private Optional<AtomTask> atomTask;
		
		public static AuthenState success(EmployeeImport employeeImport, User user) {
			return new AuthenState(true, employeeImport, user, Optional.empty());
		}
		
		public static AuthenState failed(AtomTask atomTask) {
			return new AuthenState(false, null, null, Optional.of(atomTask));
		}
	}
	
	@Value
	static class AuthorResult implements LoginCommandHandlerBase.AuthorizationResult<CheckChangePassDto> {

		Optional<AtomTask> atomTask;
		CheckChangePassDto loginResult;
		
		public static AuthorResult of(CheckChangePassDto loginResult) {
			return new AuthorResult(Optional.empty(), loginResult);
		}
	}
	
	public static interface Require extends
			AuthenticateEmployeePassword.Require,
			FindUser.Require,
			LoginCommandHandlerBase.Require {
	}
	
	@Override
	protected Require getRequire(PasswordAuthenticateCommand command) {
		return EmbedStopwatch.embed(new RequireImpl(command.getContractCode()));
	}
	
	@Value
	public class RequireImpl implements Require {
		
		private final String tenantCode;

		@Override
		public Optional<String> getPersonalId(String companyId, String employeeCode) {
			return employeeAdapter.getCurrentInfoByScd(companyId, employeeCode)
					.map(e -> e.getPersonalId());
		}

		@Override
		public Optional<User> getUser(String personalId) {
			return userRepository.getByAssociatedPersonId(personalId);
		}

		@Override
		public CompanyInformationImport getCompanyInformationImport(String companyId) {
			return companyInformationAdapter.findById(companyId);
		}

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepository.find(tenantCode);
		}

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode, GeneralDate date) {
			return tenantAuthenticationRepository.find(tenantCode, date);
		}

		
		@Override
		public Optional<RoleInfoImport> getRoleInfoImport(String userId, int roleType, GeneralDate baseDate, String comId) {
			return roleFromUserIdAdapter.getRoleInfoFromUser(userId, roleType, baseDate, comId);
		}
		

		@Override
		public String getRoleId(String userId, Integer roleType, GeneralDate baseDate) {
			return roleFromUserIdAdapter.getRoleFromUser(userId, roleType, baseDate);
		}

		@Override
		public String getLoginUserContractCode() {
			return tenantCode;
		}

		@Override
		public AuthenticationFailuresLog getAuthenticationFailuresLog(String userId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<AccountLockPolicy> getAccountLockPolicy(String contractCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void save(AuthenticationFailuresLog failuresLog) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void save(LockOutData lockOutData) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public CompanyInforImport getCompanyInforImport(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<PlannedOutageByTenant> getPlannedOutageByTenant(String tenantCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<PlannedOutageByCompany> getPlannedOutageByCompany(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LoginUserRoles getLoginUserRoles() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}