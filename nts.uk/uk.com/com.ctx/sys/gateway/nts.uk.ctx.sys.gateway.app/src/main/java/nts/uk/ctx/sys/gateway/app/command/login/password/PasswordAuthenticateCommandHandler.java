package nts.uk.ctx.sys.gateway.app.command.login.password;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.dom.login.adapter.CompanyInformationAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleFromUserIdAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleFromUserIdAdapter.RoleInfoImport;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.login.password.AuthenticateEmployeePassword;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
import nts.uk.ctx.sys.shared.dom.user.FindUser;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PasswordAuthenticateCommandHandler extends LoginCommandHandlerBase<
															PasswordAuthenticateCommand, 
															PasswordAuthenticateCommandHandler.LoginState, 
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
	protected LoginState authenticate(Require require, PasswordAuthenticateCommand command) {
		
		// 入力チェック
		checkInput(command);
		
		return passwordAuthenticate(require, command);
	}

	// 認証成功時の処理
	@Override
	protected CheckChangePassDto processSuccess(LoginState state) {
		/* ログインチェック  */
		return CheckChangePassDto.successToAuthPassword();
	}

	// 認証失敗時の処理
	@Override
	protected CheckChangePassDto processFailure(LoginState state) {
		return CheckChangePassDto.failedToAuthPassword();
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
	
	// パスワード認証
	LoginState passwordAuthenticate(AuthenticateEmployeePassword.Require require, PasswordAuthenticateCommand command) {
		
		String companyId = companyInformationAdapter.createCompanyId(command.getTenantCode(), command.getCompanyCode());
		String employeeCode = command.getEmployeeCode();
		String password = command.getPassword();
		
		
		// パスワード認証
		val successPasswordAuth = AuthenticateEmployeePassword.authenticate(require, companyId, employeeCode, password);
		if(!successPasswordAuth) {
			// パスワード認証失敗
			return LoginState.failed();
		}
		
		// パスワード認証成功
		Optional<EmployeeImport> optEmployee = employeeAdapter.getCurrentInfoByScd(companyId, employeeCode);
		Optional<User> optUser = FindUser.byEmployeeCode(require, companyId, employeeCode);
		if(!optEmployee.isPresent() || !optUser.isPresent()) {
			throw new BusinessException("Msg_318");
		}
		return LoginState.success(optEmployee.get(), optUser.get());
	}

	static class LoginState implements LoginCommandHandlerBase.AuthenticationState{
			
		private boolean isSuccess;
		
		private EmployeeImport employeeImport;
		
		private User user;
		
		public LoginState(boolean isSuccess, EmployeeImport employeeImport, User user) {
			this.isSuccess = isSuccess;
			this.employeeImport = employeeImport;
			this.user = user;
		}
		
		public static LoginState success(EmployeeImport employeeImport, User user) {
			return new LoginState(true, employeeImport, user);
		}
		
		public static LoginState failed() {
			return new LoginState(false, null, null);
		}
		
		@Override
		public boolean isSuccess() {
			return isSuccess;
		}

		@Override
		public EmployeeImport getEmployee() {
			return employeeImport;
		}
		
		@Override
		public User getUser() {
			return user;
		}	
	}
	
	public static interface Require extends AuthenticateEmployeePassword.Require, 
											FindUser.Require, 
											LoginCommandHandlerBase.Require {
	}
	
	@Override
	protected Require getRequire() {
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	public class RequireImpl implements Require {

		@Override
		public Optional<String> getPersonalId(String companyId, String employeeCode) {
			val empInfo = employeeAdapter.getCurrentInfoByScd(companyId, employeeCode);
			if(!empInfo.isPresent()) {
				return Optional.empty();
			}
			return Optional.of(empInfo.get().getPersonalId());
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
	}
}