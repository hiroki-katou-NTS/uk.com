package nts.uk.ctx.cloud.operate.dom.service;

import nts.arc.task.tran.AtomTask;

/**
 * テナントを作る
 * @author keisuke_hoshina
 *
 */
public class CreateTenantService {
	public static AtomTask create(Require require,String contractCode, String password) {
		AtomTask createLogin = nts.uk.ctx.sys.tenant.dom.service.CreateTenantService.create(require, contractCode, password);		
		return AtomTask.of(()->{
			require.saveContract();
		}).then(createLogin);
	}
	
	public static interface Require extends nts.uk.ctx.sys.tenant.dom.service.CreateTenantService.Require{
		void saveContract();
	}
}
