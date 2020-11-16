package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.CheckIfCanLogin;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.FindTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;
import nts.uk.ctx.sys.shared.dom.user.User;

/**
 * TenantLocatorを想定したログイン処理の基底クラス
 *
 * @param <Command> Command
 * @param <Authen> 認証処理の結果
 * @param <Author> 認可処理の結果
 * @param <Result> ログイン全体の結果、CommandHandlerの戻り値
 * @param <Req> Require
 */
@Stateless
public abstract class LoginCommandHandlerBase<
		Command extends LoginCommandHandlerBase.TenantAuth,
		Authen extends LoginCommandHandlerBase.AuthenticationResult,
		Author extends LoginCommandHandlerBase.AuthorizationResult<Result>,
		Result,
		Req extends LoginCommandHandlerBase.Require>
		extends CommandHandlerWithResult<Command, Result> {
	
	@Inject
	private BuildLoginEmployeeSession session;
	
	@Inject
	private TransactionService transaction;

	@Override
	protected Result handle(CommandHandlerContext<Command> context) {
		
		Command command = context.getCommand();
		
		/* テナントロケーター処理 */
		
		Req require = getRequire(command);

		// テナント認証
		val opTenant = require.getTenantAuthentication(command.getTenantCode());
		if(!opTenant.isPresent()) {
			return getResultOnFailTenantAuth();
		}
		val tenant = opTenant.get();
		
		val passwordVerify = tenant.verify(command.getTenantPasswordPlainText());		
		val available = tenant.isAvailableAt(GeneralDate.today());
		
		if(!passwordVerify || !available) {
			// テナント認証失敗
			/* テナントロケーターのdisconnect処理 */
			return getResultOnFailTenantAuth();
		}
		
		Authen authen = authenticate(require, command);
		
		Author author;
		if (authen.isSuccess()) {
			authorize(require, authen);
			session.build(require, authen.getEmployee(), authen.getUser());
			author = processSuccess(require, authen);
		} else {
			author = processFailure(require, authen);
		}
		/* ログインログ */
		
		
		transaction.execute(() -> {
			authen.getAtomTask().ifPresent(t -> t.run());
			author.getAtomTask().ifPresent(t -> t.run());
		});
		
		return author.getLoginResult();
	}
	
	private void authorize(Req require, Authen authen) {
		
		val result = CheckIfCanLogin.check(
				require,
				authen.getUser().getContractCode().v(),
				authen.getEmployee().getCompanyId(),
				authen.getEmployee().getEmployeeId());
	}
	
	/**
	 * テナント認証失敗時の処理
	 * @param 
	 * @return
	 */
	protected abstract Result getResultOnFailTenantAuth();
	
	/**
	 * 認証処理本体
	 * @param require
	 * @param command
	 * @return
	 */
	protected abstract Authen authenticate(Req require, Command command);
	
	/**
	 * 認証成功時の処理
	 * @param state
	 * @return
	 */
	protected abstract Author processSuccess(Req require, Authen state);
	
	/**
	 * 認証失敗時の処理
	 * @param state
	 * @return
	 */
	protected abstract Author processFailure(Req require, Authen state);
	
	public static interface TenantAuth {
		
		/** テナントコード */
		String getTenantCode();
		
		/** テナント認証パスワードの平文 */
		String getTenantPasswordPlainText();
		
	}
	
	public static interface AuthenticationResult {
		
		boolean isSuccess();
		
		EmployeeImport getEmployee();
		
		User getUser();
		
		Optional<AtomTask> getAtomTask();
	}
	
	public static interface AuthorizationResult<R> {

		Optional<AtomTask> getAtomTask();
		
		R getLoginResult();
	}
	
	protected abstract Req getRequire(Command command);
	
	public static interface Require extends
		BuildLoginEmployeeSession.Require,
		FindTenant.Require,
		CheckIfCanLogin.Require {
		
		Optional<TenantAuthentication> getTenantAuthentication(String tenantCode);
	}	
}
