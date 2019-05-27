package nts.uk.ctx.at.record.infra.repository.workrecord.actualsituation.createapproval.dailyperformance;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppInterrupDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppInterrupDailyRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.dailyperformance.KrcmtAppInterrupDaily;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaAppInterrupDailyRepository extends JpaRepository implements AppInterrupDailyRepository {
	private static final String SELECT_ALL_APP_INTERRUP_DAILY = " SELECT c FROM KrcmtAppInterrupDaily c ";
	private static final String SELECT_APP_INTERRUP_DAILY_BY_ID = SELECT_ALL_APP_INTERRUP_DAILY 
			+ " WHERE c.executionId = :executionId";
	@Override
	public Optional<AppInterrupDaily> getAppInterrupDailyByID(String executionId) {
		Optional<AppInterrupDaily> data = this.queryProxy().query(SELECT_APP_INTERRUP_DAILY_BY_ID,KrcmtAppInterrupDaily.class)
				.setParameter("executionId", executionId)
				.getSingle(c->c.toDomain());
		return data;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void addAppInterrupDaily(AppInterrupDaily appInterrupDaily) {
		this.commandProxy().insert(KrcmtAppInterrupDaily.toEntity(appInterrupDaily));
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateAppInterrupDaily(AppInterrupDaily appInterrupDaily) {
		KrcmtAppInterrupDaily newEntity = KrcmtAppInterrupDaily.toEntity(appInterrupDaily);
		KrcmtAppInterrupDaily updateEntity = this.queryProxy().find(newEntity.executionId, KrcmtAppInterrupDaily.class).get();
		updateEntity.suspendedState = newEntity.suspendedState;
		this.commandProxy().update(updateEntity);
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteAppInterrupDaily(String executionId) {
		Optional<AppInterrupDaily> newEntity = this.queryProxy().query(SELECT_APP_INTERRUP_DAILY_BY_ID,KrcmtAppInterrupDaily.class)
				.setParameter("executionId", executionId)
				.getSingle(c->c.toDomain());
		this.commandProxy().remove(newEntity);
	}

}
