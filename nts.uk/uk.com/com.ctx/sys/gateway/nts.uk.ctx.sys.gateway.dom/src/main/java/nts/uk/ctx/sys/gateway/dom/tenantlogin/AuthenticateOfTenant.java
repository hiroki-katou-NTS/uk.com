package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;

/**
 * テナント認証する
 * ※テナントロケータによるデータソースアクセス済みであること
 * @author hiroki_katou
 *
 */
public class AuthenticateOfTenant {
	public static TenantAuthenticateResult authenticate(Require require, String tenantCode, String password, LoginClient loginClient) {
		
		// テナントロケータに接続できている以上取得できるはず
		val optTenant = require.getTenantAuthentication(tenantCode);
		if(!optTenant.isPresent()) {
			return TenantAuthenticateResult.failedToIdentifyTenant(createFailreLog(require, tenantCode, password, loginClient));
		}
		val tenant = optTenant.get();
		// パスワード検証
		if(!tenant.verifyPassword(password)) {
			// テナントのパスワード検証に失敗
			return TenantAuthenticateResult.failedToAuthPassword(createFailreLog(require, tenantCode, password, loginClient));
		} 
		
		// 有効期限チェック
		if(!tenant.isAvailableAt(GeneralDate.today())) {
			// テナントの有効期限切れ
			return TenantAuthenticateResult.failedToExpired(createFailreLog(require, tenantCode, password, loginClient));
		}
		
		// 認証成功
		return TenantAuthenticateResult.success();
	}
	
	/**
	 * 失敗時の失敗記録生成
	 * @param require
	 * @param tenantCode
	 * @param password
	 * @param triedLoginClient
	 * @return
	 */
	private static AtomTask createFailreLog(Require require, String tenantCode, String password, LoginClient triedLoginClient) {
		val failureLog = TenantAuthenticateFailureLog.failedNow(triedLoginClient, tenantCode, password);
		return AtomTask.of(() -> {
			require.insert(failureLog);
		});
	}
	
	public static interface Require {
		Optional<TenantAuthenticate> getTenantAuthentication(String tenantCode);
		void insert(TenantAuthenticateFailureLog failureLog);
	}
}
