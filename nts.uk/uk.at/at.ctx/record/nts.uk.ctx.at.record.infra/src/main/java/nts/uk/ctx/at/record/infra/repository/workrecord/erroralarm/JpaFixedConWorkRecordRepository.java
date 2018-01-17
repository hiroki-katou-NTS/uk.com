package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KrcmtFixedConditionWorkRecord;

@Stateless
public class JpaFixedConWorkRecordRepository extends JpaRepository implements  FixedConditionWorkRecordRepository {

	private final String SELECT_FROM_FIXED_CON = " SELECT c FROM KrcmtFixedConditionWorkRecord c ";
	
	private final String SELECT_FIXED_CON_BY_ALARM_ID =SELECT_FROM_FIXED_CON 
			+ " WHERE c.errorAlarmID = :errorAlarmID ";
	
	@Override
	public List<FixedConditionWorkRecord> getAllFixedConditionWorkRecord() {
		List<FixedConditionWorkRecord> data = this.queryProxy().query(SELECT_FROM_FIXED_CON,KrcmtFixedConditionWorkRecord.class)
				.getList(c->c.toDomain());
		return data;
	}



	@Override
	public Optional<FixedConditionWorkRecord> getFixedConWRByCode(String errorAlarmID) {
		Optional<FixedConditionWorkRecord> data = this.queryProxy().query(SELECT_FIXED_CON_BY_ALARM_ID,KrcmtFixedConditionWorkRecord.class)
				.setParameter("errorAlarmID", errorAlarmID)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void addFixedConWorkRecord(FixedConditionWorkRecord fixedConditionWorkRecord) {
		this.commandProxy().insert(KrcmtFixedConditionWorkRecord.toEntity(fixedConditionWorkRecord));
		this.getEntityManager().flush();
		
	}

	@Override
	public void updateFixedConWorkRecord(FixedConditionWorkRecord fixedConditionWorkRecord) {
		KrcmtFixedConditionWorkRecord newEntity = KrcmtFixedConditionWorkRecord.toEntity(fixedConditionWorkRecord);
		KrcmtFixedConditionWorkRecord updateEntity = this.queryProxy().find(
				newEntity.errorAlarmID,
				KrcmtFixedConditionWorkRecord.class).get();
		updateEntity.message = newEntity.message;
		updateEntity.useAtr = newEntity.useAtr;
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void deleteFixedConWorkRecord(String errorAlarmID) {
		this.commandProxy().remove(KrcmtFixedConditionWorkRecord.class,
				errorAlarmID);
		
	}

}
