package nts.uk.ctx.at.record.infra.repository.workrecord.actualsituation.createapproval.monthlyperformance;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMon;
import nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.monthlyperformance.KrcmtAppInterrupMon;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AddAppInterrupMonth extends JpaRepository{
	
	public void addAppInterrupMon(AppInterrupMon appInterrupMon) {
		this.commandProxy().insert(KrcmtAppInterrupMon.toEntity(appInterrupMon));
		this.getEntityManager().flush();
	}
}
