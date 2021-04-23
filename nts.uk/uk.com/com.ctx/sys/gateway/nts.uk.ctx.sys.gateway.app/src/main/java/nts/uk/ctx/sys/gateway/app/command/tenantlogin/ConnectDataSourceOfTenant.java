package nts.uk.ctx.sys.gateway.app.command.tenantlogin;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.AuthenticateOfTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.AuthenticateOfTenant.Require;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateFailureLog;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateResult;
import nts.uk.shr.com.system.property.UKServerSystemProperties;
import nts.uk.shr.infra.data.TenantLocatorService;

/**
 * テナントのデータソースに接続する
 * @author hiroki_katou
 *
 */
public class ConnectDataSourceOfTenant {
	public static TenantAuthenticateResult connect(Require require, LoginClient loginClient, String tenantCode, String password) {
		
		/* テナントロケーター処理 */
		if (UKServerSystemProperties.usesTenantLocator()) {
			// テナント認証するため、一旦接続する
			
			TenantLocatorService.connect(tenantCode);
			// テナントの特定に失敗
			if(!TenantLocatorService.isConnected()) {
				// 失敗記録
				val failureLog = TenantAuthenticateFailureLog.failedNow(loginClient, tenantCode, password);
				val atomTask = AtomTask.of(() -> {
					require.insert(failureLog);
				});
				return TenantAuthenticateResult.failedToIdentifyTenant(atomTask);
			}
		}
		
		// テナント認証
		val result = AuthenticateOfTenant.authenticate(require, tenantCode, password, loginClient);
		if(result.isFailure()) {
			if (UKServerSystemProperties.usesTenantLocator()) {
				// テナント認証に失敗した場合、データソースとの接続を切断する
				TenantLocatorService.disconnect();
			}
		}
		return result;
	}
}
