package nts.uk.ctx.sys.gateway.app.command.tenantlogin;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.AuthenticateOfTenant;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.AuthenticateOfTenant.Require;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationResult;
import nts.uk.shr.com.system.property.UKServerSystemProperties;
import nts.uk.shr.infra.data.TenantLocatorService;

/**
 * テナントのデータソースに接続する
 * @author hiroki_katou
 *
 */
public class ConnectDataSourceOfTenant {
	public static TenantAuthenticationResult connect(Require require, LoginClient loginClient, String tenantCode, String password) {
		/* テナントロケーター処理 */
		if (UKServerSystemProperties.usesTenantLocator()) {
			// テナント認証するため、一旦接続する
			TenantLocatorService.connect(tenantCode);
		}
		
		// テナント認証
		val result = AuthenticateOfTenant.authenticate(require, loginClient, tenantCode, password);
		if(result.isFailure()) {
			if (UKServerSystemProperties.usesTenantLocator()) {
				// テナント認証に失敗した場合、データソースとの接続を切断する
				TenantLocatorService.disconnect();
			}
		}
		return result;
	}
}
