package nts.uk.ctx.at.record.infra.repository.workrecord.actualsituation.createapproval.monthlyperformance;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMon;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMonRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.monthlyperformance.KrcmtAppInterrupMon;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaAppInterrupMonRepository extends JpaRepository implements AppInterrupMonRepository {
	private static final String SELECT_ALL_APP_INTERRUP_MON = " SELECT c FROM KrcmtAppInterrupMon c ";
	private static final String SELECT_APP_INTERRUP_MON_BY_ID = SELECT_ALL_APP_INTERRUP_MON 
			+ " WHERE c.executionId = :executionId";
	@Override
	public Optional<AppInterrupMon> getAppInterrupMonByID(String executionId) {
		Optional<AppInterrupMon> data = this.queryProxy().query(SELECT_APP_INTERRUP_MON_BY_ID,KrcmtAppInterrupMon.class)
				.setParameter("executionId", executionId)
				.getSingle(c->c.toDomain());
		return data;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void addAppInterrupMon(AppInterrupMon appInterrupMon) {
		this.commandProxy().insert(KrcmtAppInterrupMon.toEntity(appInterrupMon));
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateAppInterrupMon(AppInterrupMon appInterrupMon) {
		KrcmtAppInterrupMon newEntity = KrcmtAppInterrupMon.toEntity(appInterrupMon);
		KrcmtAppInterrupMon updateEntity = this.queryProxy().find(newEntity.executionId, KrcmtAppInterrupMon.class).get();
		updateEntity.suspendedState = newEntity.suspendedState;
		this.commandProxy().update(updateEntity);
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteAppInterrupMon(String executionId) {
		Optional<AppInterrupMon> newEntity = this.queryProxy().query(SELECT_APP_INTERRUP_MON_BY_ID,KrcmtAppInterrupMon.class)
				.setParameter("executionId", executionId)
				.getSingle(c->c.toDomain());
		this.commandProxy().remove(newEntity);
	}

}
