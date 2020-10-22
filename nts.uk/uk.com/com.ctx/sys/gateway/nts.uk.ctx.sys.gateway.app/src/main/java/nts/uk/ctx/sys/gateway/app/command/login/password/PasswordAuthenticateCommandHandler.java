package nts.uk.ctx.sys.gateway.app.command.login.password;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.app.command.loginold.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.login.password.AuthenticateEmployeePassword;
import nts.uk.ctx.sys.shared.dom.user.FindUser;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PasswordAuthenticateCommandHandler 
	extends LoginCommandHandlerBase<PasswordAuthenticateCommand, PasswordAuthenticateCommandHandler.LoginState, CheckChangePassDto>{
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private SysEmployeeAdapter employeeAdapter;
	
	// テナント認証失敗時
	@Override
	protected CheckChangePassDto getResultOnFailTenantAuth() {
		return CheckChangePassDto.failedToAuthTenant();
	}
	
	// 認証処理
	@Override
	protected LoginState processBeforeLogin(PasswordAuthenticateCommand command) {
		
		// 入力チェック
		checkInput(command);
		
		Require require = EmbedStopwatch.embed(new RequireImpl());
		
		return passwordAuthenticate(require, command);
	}

	LoginState passwordAuthenticate(AuthenticateEmployeePassword.Require require, PasswordAuthenticateCommand command) {
		String companyCode = command.getCompanyCode();
		String tenantCode = command.getTenantCode();
		String companyId = tenantCode + "-" + companyCode;
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

	@Override
	protected CheckChangePassDto processSuccess(LoginState state) {
		/* ログインチェック  */
		/* ログインログ  */
		return CheckChangePassDto.successToAuthPassword();
	}

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
	
	static class LoginState implements LoginCommandHandlerBase.LoginState<PasswordAuthenticateCommand>{
			
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
	
	public static interface Require extends AuthenticateEmployeePassword.Require, FindUser.Require {
		
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
	}
}