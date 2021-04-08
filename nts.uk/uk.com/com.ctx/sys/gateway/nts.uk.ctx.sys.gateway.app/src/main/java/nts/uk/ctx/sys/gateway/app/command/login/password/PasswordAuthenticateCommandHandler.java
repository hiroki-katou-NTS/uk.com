package nts.uk.ctx.sys.gateway.app.command.login.password;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Value;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticateWithEmployeeCode;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticateResult;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PasswordAuthenticateCommandHandler extends LoginCommandHandlerBase<
															PasswordAuthenticateCommand, 
															PasswordAuthenticateCommandHandler.Authentication,
															CheckChangePassDto, 
															PasswordAuthenticateCommandHandler.Require> {
	
	@Inject
	private TransactionService transaction;
	
	@Inject
	private PasswordAuthenticateCommandRequire requireProvider;
	
	@Inject
	private LoginBuiltInUser loginBuiltInUser;
	
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
		
		// ビルトインユーザはこちらへ
		if (require.getBuiltInUser(tenantCode, companyId).authenticate(employeeCode, password)) {
			return Authentication.asBuiltInUser(tenantCode, companyId);
		}
		
		// ログイン社員の識別
		val idenResult = EmployeeIdentify.identifyByEmployeeCode(require, companyId, employeeCode);
		
		if(idenResult.isFailed()) {
			transaction.execute(() ->{
				idenResult.getFailureLog().get();
				
			});
		}
		
		// パスワード認証
		val authenticationResult = PasswordAuthenticateWithEmployeeCode.authenticate(
				require, 
				idenResult.getEmployeeInfo().get(), 
				password);
		
		
		
		
		return Authentication.of(authenticationResult);
	}
	
	/**
	 * ビルトインユーザのための処理を組み込むためにoverride
	 */
	@Override
	protected AtomTask authorize(Require require, Authentication authen) {
		
		if (authen.isBuiltInUser()) {
			loginBuiltInUser.login(
					require,
					authen.getTenantCodeForBuiltInUser(),
					authen.getCompanyIdForBuiltInUser());
			
			return AtomTask.none();
		}

		// 通常はsuper側に任せる
		return super.authorize(require, authen);
	}

	/**
	 * 認証失敗時の処理
	 */
	@Override
	protected CheckChangePassDto authenticationFailed(Require require, Authentication authen) {
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
		
		// ビルトインユーザか
		private boolean isBuiltInUser;
		private String tenantCodeForBuiltInUser;
		private String companyIdForBuiltInUser;
		private PasswordAuthenticateResult authenResult;
		
		public static Authentication of(PasswordAuthenticateResult authenResult) {
			return new Authentication(false, null, null, authenResult);
		}
		
		public static Authentication asBuiltInUser(String tenantCode, String companyId) {
			return new Authentication(true, tenantCode, companyId, null);
		}

		@Override
		public boolean isSuccess() {
			return isBuiltInUser || authenResult.isSuccess();
		}

		@Override
		public IdentifiedEmployeeInfo getIdentified() {
			return authenResult.getIdentified().get();
		}
	}
	
	public static interface Require extends 
			PasswordAuthenticateWithEmployeeCode.Require,
			LoginCommandHandlerBase.Require,
			LoginBuiltInUser.RequireLogin {
		
		String createCompanyId(String tenantCode, String companyCode);
	}
	
}