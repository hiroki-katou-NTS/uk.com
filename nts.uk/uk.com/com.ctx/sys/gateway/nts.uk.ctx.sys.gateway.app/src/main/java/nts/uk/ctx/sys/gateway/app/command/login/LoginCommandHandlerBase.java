package nts.uk.ctx.sys.gateway.app.command.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.FindTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
import nts.uk.shr.com.context.loginuser.SessionLowLayer;

/**
 * TenantLocatorを想定したログイン処理の基底クラス
 *
 * @param <C> Command
 * @param <R> Result
 */
@Stateless
public abstract class LoginCommandHandlerBase<
		C extends LoginCommandHandlerBase.TenantAuth,
		S extends LoginCommandHandlerBase.LoginState<C>,
		R >
		extends CommandHandlerWithResult<C, R> {
	
	@Inject
	private TenantAuthenticationRepository tenantAuthenticationRepository;
	
	@Inject
	private SessionLowLayer sessionLowLayer;

	@Override
	protected R handle(CommandHandlerContext<C> context) {
		
		C command = context.getCommand();

		// テナント認証
		val require = EmbedStopwatch.embed(new RequireImpl());
		boolean successTenantAuth = FindTenant.byTenantCode(require, command.getTenantCode())
				.map(t -> t.verify(command.getTenantPasswordPlainText()))
				.orElse(false);
		
		if(!successTenantAuth) {
			// テナント認証失敗
			return getResultOnFailTenantAuth();
		}

		
		/* テナントロケーター処理 */
		
		S state = processBeforeLogin(command);
		
		if (state.isSuccess()) {
			initSession(state);
			return processSuccess(state);
		} else {
			return processFailure(state);
		}
		
	}
	
	private void initSession(S state) {
		
		sessionLowLayer.loggedIn();
		
		/* 社員IDとかロールとか、セッションに持たせる情報をセット */
		
		//EmployeeImport employee = state.getEmployee();
	}
	
	protected abstract R getResultOnFailTenantAuth();
	
	/**
	 * ログイン（認証）処理本体
	 * @param command
	 * @return
	 */
	protected abstract S processBeforeLogin(C command);
	
	/**
	 * ログイン成功時の処理
	 * @param state
	 * @return
	 */
	protected abstract R processSuccess(S state);
	
	/**
	 * ログイン失敗時の処理
	 * @param state
	 * @return
	 */
	protected abstract R processFailure(S state);
	
	public static interface TenantAuth {
		
		/** テナントコード */
		String getTenantCode();
		
		/** テナント認証パスワードの平文 */
		String getTenantPasswordPlainText();
		
	}
	
	public static interface LoginState<R> {
		
		boolean isSuccess();
		
		EmployeeImport getEmployee();
		
		// User (sys.shared) getUser();
	}
	
	public class RequireImpl implements FindTenant.Require{

		@Override
		public Optional<TenantAuthentication> getTenantAuthentication(String tenantCode) {
			return tenantAuthenticationRepository.find(tenantCode);
		}

	}

}
