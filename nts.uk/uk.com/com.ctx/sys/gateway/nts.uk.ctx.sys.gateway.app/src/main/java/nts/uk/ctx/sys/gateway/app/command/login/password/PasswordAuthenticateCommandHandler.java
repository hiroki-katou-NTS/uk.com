package nts.uk.ctx.sys.gateway.app.command.login.password;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticateResult;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticateWithEmployeeCode;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify.IdentificationResult;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PasswordAuthenticateCommandHandler extends LoginCommandHandlerBase<
															PasswordAuthenticateCommand, 
															AuthenticateResult,
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
	protected AuthenticateResult authenticate(Require require, PasswordAuthenticateCommand command) {
		
		// 入力チェック
		command.checkInput();
		
		String tenantCode = command.getTenantCode();
		String companyId = require.createCompanyId(tenantCode, command.getCompanyCode());
		String employeeCode = command.getEmployeeCode();
		String password = command.getPassword();
		
		// ビルトインユーザはこちらへ
		if (require.getBuiltInUser(tenantCode, companyId).authenticate(employeeCode, password)) {
			return AuthenticateResult.asBuiltInUser(tenantCode, companyId);
		}
		
		// ログイン社員の識別
		IdentificationResult idenResult = EmployeeIdentify.identifyByEmployeeCode(require, companyId, employeeCode);
		
		if(idenResult.isFailed()) {
			transaction.execute(() ->{
				idenResult.getFailureLog().get();
			});
			return AuthenticateResult.identificationFailure(idenResult);
		}
		
		// パスワード認証
		PasswordAuthenticateResult passAuthResult = PasswordAuthenticateWithEmployeeCode.authenticate(
				require, 
				idenResult.getEmployeeInfo().get(), 
				password);
		
		if(passAuthResult.isFailed()) {

			return AuthenticateResult.passAuthenticateFailure(idenResult, passAuthResult);
		}
			
		return AuthenticateResult.success(idenResult, passAuthResult);
	}
	
	/**
	 * ビルトインユーザのための処理を組み込むためにoverride
	 */
	@Override
	protected void authorize(Require require, AuthenticateResult authen) {
		
		if (authen.isBuiltInUser()) {
			loginBuiltInUser.login(
					require,
					authen.getTenantCodeForBuiltInUser(),
					authen.getCompanyIdForBuiltInUser());
		}

		// 通常はsuper側に任せる
		super.authorize(require, authen);
	}

	/**
	 * 認証失敗時の処理
	 */
	@Override
	protected CheckChangePassDto authenticationFailed(Require require, AuthenticateResult authen) {
		return CheckChangePassDto.failedToAuthPassword();
	}

	/**
	 * ログイン成功時の処理
	 */
	@Override
	protected CheckChangePassDto loginCompleted(Require require, AuthenticateResult authen) {
		return CheckChangePassDto.successToAuthPassword();
	}

	public static interface Require extends PasswordAuthenticateWithEmployeeCode.Require,
											LoginCommandHandlerBase.Require,
											LoginBuiltInUser.RequireLogin {
		
		String createCompanyId(String tenantCode, String companyCode);
	}
}