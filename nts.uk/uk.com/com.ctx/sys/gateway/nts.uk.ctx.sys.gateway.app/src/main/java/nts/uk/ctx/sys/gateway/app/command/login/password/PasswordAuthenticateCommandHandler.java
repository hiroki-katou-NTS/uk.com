package nts.uk.ctx.sys.gateway.app.command.login.password;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Value;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.AuthenticateEmployeePassword;
import nts.uk.ctx.sys.gateway.dom.login.password.AuthenticateEmployeePasswordResult;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PasswordAuthenticateCommandHandler extends LoginCommandHandlerBase<
															PasswordAuthenticateCommand, 
															PasswordAuthenticateCommandHandler.Authentication,
															CheckChangePassDto, 
															PasswordAuthenticateCommandHandler.Require> {
	
	@Inject
	private PasswordAuthenticateCommandRequire requireProvider;
	
	@Override
	protected Require getRequire(PasswordAuthenticateCommand command) {
		return requireProvider.createRequire(command.getContractCode());
	}
	
	/**
	 * テナント認証失敗時の処理
	 */
	@Override
	protected CheckChangePassDto tenantAuthencationFailed() {
		return CheckChangePassDto.failedToAuthTenant();
	}
	
	/**
	 * 認証処理本体
	 */
	@Override
	protected Authentication authenticate(Require require, PasswordAuthenticateCommand command) {
		
		// 入力チェック
		command.checkInput();

		String tenantCode = command.getTenantCode();
		String companyId = require.createCompanyId(tenantCode, command.getCompanyCode());
		String employeeCode = command.getEmployeeCode();
		String password = command.getPassword();
		
		// パスワード認証
		val authenticationResult = AuthenticateEmployeePassword.authenticate(
				require, tenantCode, companyId, employeeCode, password);
		
		return Authentication.of(authenticationResult);
	}

	/**
	 * 社員認証失敗時の処理
	 */
	@Override
	protected CheckChangePassDto employeeAuthenticationFailed(Require require, Authentication authen) {
		return CheckChangePassDto.failedToAuthPassword();
	}

	/**
	 * ログイン成功時の処理
	 */
	@Override
	protected CheckChangePassDto loginCompleted(Require require, Authentication authen) {
		return CheckChangePassDto.successToAuthPassword();
	}

	@Value
	static class Authentication implements LoginCommandHandlerBase.AuthenticationResult {
			
		private AuthenticateEmployeePasswordResult authenResult;
		
		public static Authentication of(AuthenticateEmployeePasswordResult authenResult) {
			return new Authentication(authenResult);
		}

		@Override
		public boolean isSuccess() {
			return authenResult.isSuccess();
		}

		@Override
		public IdentifiedEmployeeInfo getIdentified() {
			return authenResult.getIdentified().get();
		}

		@Override
		public Optional<AtomTask> getAtomTask() {
			return Optional.of(authenResult.getAtomTask());
		}
	}
	
	public static interface Require extends
			AuthenticateEmployeePassword.Require,
			LoginCommandHandlerBase.Require {
		
		String createCompanyId(String tenantCode, String companyCode);
	}
	
}