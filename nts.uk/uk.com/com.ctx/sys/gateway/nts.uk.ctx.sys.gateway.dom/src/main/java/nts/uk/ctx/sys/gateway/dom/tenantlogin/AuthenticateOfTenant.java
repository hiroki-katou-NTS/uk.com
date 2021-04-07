package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;

/**
 * テナント認証する
 * ※テナントロケータによるデータソースアクセス済みであること
 * @author hiroki_katou
 *
 */
public class AuthenticateOfTenant {
	public static TenantAuthenticationResult authenticate(Require require, LoginClient loginClient, String tenantCode, String password) {
		
		// テナントロケータに接続できている以上取得できるはず
		val tenant = require.getTenantAuthentication(tenantCode).get();
		
		// 認証処理
		if(tenant.authentication(password)) {
			// テナント認証成功
			return TenantAuthenticationResult.success();
		} 
		else {
			// テナント認証失敗
			val failureLog = TenantAuthenticationFailureLog.failedNow(loginClient, tenantCode, password);
			val atomTask = AtomTask.of(() -> {
				require.insert(failureLog);
			});
			
			return TenantAuthenticationResult.failed(atomTask);
		}
	}
	
	public static interface Require {
		Optional<TenantAuthentication> getTenantAuthentication(String tenantCode);
		void insert(TenantAuthenticationFailureLog failureLog);
	}
}
