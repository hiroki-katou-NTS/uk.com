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
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.shr.infra.data.TenantLocatorService;

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
		Result,
		Req extends LoginCommandHandlerBase.Require>
		extends CommandHandlerWithResult<Command, Result> {
	
	
	@Inject
	private TransactionService transaction;

	@Override
	protected Result handle(CommandHandlerContext<Command> context) {
		
		Command command = context.getCommand();
		
		/* テナントロケーター処理 */
		TenantLocatorService.connect(command.getTenantCode());
		
		Req require = getRequire(command);

		// テナント認証
		val opTenant = require.getTenantAuthentication(command.getTenantCode());
		if(!opTenant.isPresent()) {
			return tenantAuthencationFailed();
		}
		val tenant = opTenant.get();
		
		val passwordVerify = tenant.verify(command.getTenantPasswordPlainText());		
		val available = tenant.isAvailableAt(GeneralDate.today());
		
		if(!passwordVerify || !available) {
			// テナント認証失敗
			/* テナントロケーターのdisconnect処理 */
			TenantLocatorService.disconnect();
			return tenantAuthencationFailed();
		}
		
		Authen authen = authenticate(require, command);
		
		if (!authen.isSuccess()) {
			authen.getAtomTask().ifPresent(t -> transaction.execute(t));
			return employeeAuthenticationFailed(require, authen);
		}
		
		// 認可
		AtomTask authorTask = authorize(require, authen);
		
		/* ログインログ */
		
		
		transaction.execute(() -> {
			authen.getAtomTask().ifPresent(t -> t.run());
			authorTask.run();
		});
		
		return loginCompleted(require, authen);
	}
	
	/**
	 * 認可処理（override可能）
	 * 認証が終わった社員に対して、実際にログインできるかの判定と、権限付与（セッション構築）を行う
	 * @param require
	 * @param authen
	 * @return
	 */
	protected AtomTask authorize(Req require, Authen authen) {
		
		val result = CheckIfCanLogin.check(require, authen.getIdentified());
		
		// セッション構築
		require.authorizeLoginSession(authen.getIdentified());
		
		return AtomTask.none();
	}
	
	/**
	 * テナント認証失敗時の処理
	 * @param 
	 * @return
	 */
	protected abstract Result tenantAuthencationFailed();
	
	/**
	 * 認証処理本体
	 * @param require
	 * @param command
	 * @return
	 */
	protected abstract Authen authenticate(Req require, Command command);

	/**
	 * 社員認証失敗時の処理
	 * @param authen
	 * @return
	 */
	protected abstract Result employeeAuthenticationFailed(Req require, Authen authen);
	
	/**
	 * ログイン成功時の処理
	 * @param authen
	 * @return
	 */
	protected abstract Result loginCompleted(Req require, Authen authen);
	
	
	public static interface TenantAuth {
		
		/** テナントコード */
		String getTenantCode();
		
		/** テナント認証パスワードの平文 */
		String getTenantPasswordPlainText();
		
	}
	
	public static interface AuthenticationResult {
		
		boolean isSuccess();
		
		IdentifiedEmployeeInfo getIdentified();
		
		Optional<AtomTask> getAtomTask();
	}
	
	public static interface AuthorizationResult<R> {

		Optional<AtomTask> getAtomTask();
		
		R getLoginResult();
	}
	
	protected abstract Req getRequire(Command command);
	
	public static interface Require extends
		CheckIfCanLogin.Require {
		
		Optional<TenantAuthentication> getTenantAuthentication(String tenantCode);
		
		void authorizeLoginSession(IdentifiedEmployeeInfo identified);
	}	
}
