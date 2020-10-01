package nts.uk.ctx.sys.tenant.dom;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;

/**
 * テナントを作る
 */
public class CreateTenant {
	
	public static AtomTask create(
			Require require,
			String tenantCode,
			String tenantPassword,
			String administratorLoginId,
			String administratorPassword) {
		
		if (require.existsTenant(tenantCode)) {
			throw new BusinessException(
					new RawErrorMessage("契約コード " + tenantCode + " は既に存在します。"));
		}

		val tenantAuthentication = TenantAuthentication.create(tenantCode, tenantPassword);
		
		val tenantAdministartor = CreateTenantAdministrator.create(
				require, tenantCode, administratorLoginId, administratorPassword);
		
		return AtomTask.of(() -> {
			
			require.add(tenantAuthentication);
			
		}).then(tenantAdministartor);
	}

	
	public static interface Require extends CreateTenantAdministrator.Require {
		
		boolean existsTenant(String tenantCode);
		
		void add(TenantAuthentication tenantAuthentication);
	}
}
