package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.gul.web.HttpClientIpAddress;
import nts.uk.ctx.sys.gateway.app.command.tenantlogin.ConnectDataSourceOfTenant;
import nts.uk.ctx.sys.gateway.dom.login.CheckIfCanLogin;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.AuthenticateOfTenant;
import nts.uk.shr.com.net.Ipv4Address;

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
		Req require = getRequire(command);
		val request = command.getRequest();
		
		// ログインクライアントの生成
		val loginClient = new LoginClient(
				Ipv4Address.parse(HttpClientIpAddress.get(request)), 
				request.getHeader("user-agent"));
		
		// テナント認証
		val tenantAuthResult = ConnectDataSourceOfTenant.connect(
				require, loginClient, command.getTenantCode(), command.getTenantPasswordPlainText());
		if(tenantAuthResult.isFailure()) {
			transaction.execute(() -> {
				tenantAuthResult.getAtomTask().get().run();
			});
			return tenantAuthencationFailed();
		}
		
		// 認証
		Authen authen = authenticate(require, command);
		if (!authen.isSuccess()) {
			return authenticationFailed(require, authen);
		}
		
		// 認可
		AtomTask authorTask = authorize(require, authen);

		
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
		
		// ログインできるかチェックする
		CheckIfCanLogin.check(require, authen.getIdentified());
		
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
	 * 認証失敗時の処理
	 * @param authen
	 * @return
	 */
	protected abstract Result authenticationFailed(Req require, Authen authen);
	
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
		
		/** ログインクライアント */
		HttpServletRequest getRequest();
		
	}
	
	public static interface AuthenticationResult {
		/** 認証成功したか */
		boolean isSuccess();
		/** 識別された社員 */
		IdentifiedEmployeeInfo getIdentified();
	}
	
	public static interface AuthorizationResult<R> {

		Optional<AtomTask> getAtomTask();
		
		R getLoginResult();
	}
	
	protected abstract Req getRequire(Command command);
	
	public static interface Require extends
		CheckIfCanLogin.Require, 
		AuthenticateOfTenant.Require{
		
		void authorizeLoginSession(IdentifiedEmployeeInfo identified);
	}	
}
