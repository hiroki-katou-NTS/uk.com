package nts.uk.ctx.sys.gateway.app.command.login;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;
import nts.uk.shr.com.context.loginuser.SessionLowLayer;

/**
 * TenantLocatorを想定したログイン処理の基底クラス
 *
 * @param <C> Command
 * @param <R> Result
 */
public abstract class LoginCommandHandlerBase<C, S extends LoginCommandHandlerBase.LoginState<C>, R>
		extends CommandHandlerWithResult<C, R> {
	
	@Inject
	private SessionLowLayer sessionLowLayer;

	@Override
	protected R handle(CommandHandlerContext<C> context) {
		
		C command = context.getCommand();
		
		String tenantCode = getTenantCode(command);
		
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
		
		EmployeeImport employee = state.getEmployee();
	}
	
	/**
	 * TenantLocatorとの仮接続のためテナントコードを返す
	 * @param command
	 * @return
	 */
	protected abstract String getTenantCode(C command);
	
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
	
	public static interface LoginState<R> {
		
		boolean isSuccess();
		
		EmployeeImport getEmployee();
		
		// User (sys.shared) getUser();
	}
}
