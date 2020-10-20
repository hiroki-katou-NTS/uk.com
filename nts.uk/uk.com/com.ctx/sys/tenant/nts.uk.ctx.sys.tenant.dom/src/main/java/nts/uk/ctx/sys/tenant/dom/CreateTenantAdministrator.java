package nts.uk.ctx.sys.tenant.dom;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.shared.dom.user.User;

/**
 * テナント管理者を作る
 */
public class CreateTenantAdministrator {

	public static AtomTask create(
			Require require,
			String tenantCode,
			String loginId,
			String passwordPlainText) {
		
		if (require.existsUser(tenantCode, loginId)) {
			throw new BusinessException("Msg_61", loginId);
		}
		
		val builtInUser = User.createBuiltInUser(tenantCode, loginId, passwordPlainText);
		
		return AtomTask.of(() -> {
			
			require.addBuiltInUser(builtInUser);
			
		});
	}
	
	public static interface Require {
		
		boolean existsUser(String tenantCode, String loginId);
		
		void addBuiltInUser(User builtInUser);
	}
}
