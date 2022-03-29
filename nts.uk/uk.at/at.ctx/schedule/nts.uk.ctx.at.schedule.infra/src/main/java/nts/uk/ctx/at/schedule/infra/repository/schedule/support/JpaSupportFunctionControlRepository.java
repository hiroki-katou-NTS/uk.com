/**
 * 
 */
package nts.uk.ctx.at.schedule.infra.repository.schedule.support;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.support.SupportFunctionControl;
import nts.uk.ctx.at.schedule.dom.schedule.support.SupportFunctionControlRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.support.KscmtSupportFuncCtrl;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaSupportFunctionControlRepository extends JpaRepository implements SupportFunctionControlRepository{

	
	private SupportFunctionControl toDomain(KscmtSupportFuncCtrl entity){
		SupportFunctionControl domain = new SupportFunctionControl(entity.useSupport, entity.useSupportInTimeZone);
		return domain;
	}
	
	@Override
	public SupportFunctionControl get(String cid) {
		return this.queryProxy().find(cid, KscmtSupportFuncCtrl.class).map(x -> toDomain(x)).orElse(null);
	}

	@Override
	public void update(String cid, SupportFunctionControl domain) {
		
		Optional<KscmtSupportFuncCtrl> opt = this.queryProxy().find(cid, KscmtSupportFuncCtrl.class);
		if(opt.isPresent()){
			KscmtSupportFuncCtrl entity = opt.get();
			entity.useSupport = domain.isUse();
			entity.useSupportInTimeZone = domain.isUseSupportInTimezone();
			this.commandProxy().update(entity);
		}
	}

}
