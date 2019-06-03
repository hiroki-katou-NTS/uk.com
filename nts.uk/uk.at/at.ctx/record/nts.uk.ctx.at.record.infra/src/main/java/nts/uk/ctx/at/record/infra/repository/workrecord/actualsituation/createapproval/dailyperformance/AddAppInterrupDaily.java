package nts.uk.ctx.at.record.infra.repository.workrecord.actualsituation.createapproval.dailyperformance;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppInterrupDaily;
import nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.dailyperformance.KrcmtAppInterrupDaily;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AddAppInterrupDaily extends JpaRepository{
	
	public void addAppInterrupDaily(AppInterrupDaily appInterrupDaily) {
		this.commandProxy().insert(KrcmtAppInterrupDaily.toEntity(appInterrupDaily));
		this.getEntityManager().flush();
	}
}
