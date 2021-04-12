package nts.uk.ctx.sys.tenant.dom.service;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticate;
import nts.uk.shr.com.tenant.event.CreatedTenantEvent;

/**
 * テナントを作る
 * @author keisuke_hoshina
 */
public class CreateTenantService{
	
	public static AtomTask create(Require require,String contractCode, String plainTextPassword) {
		//イベント作る
		CreatedTenantEvent event = new CreatedTenantEvent(contractCode);
		//イベント発行
		AtomTask eventTask = event.publish();
		
		TenantAuthenticate tenantAuthentication = TenantAuthenticate.create(contractCode, plainTextPassword, GeneralDate.today());
		return AtomTask.of(()->{
			//テナント認証作成
			require.save(tenantAuthentication);
		}).then(eventTask);
	}
	
	public static interface Require{
		void save(TenantAuthenticate tenantAuthentication);
	}
}
