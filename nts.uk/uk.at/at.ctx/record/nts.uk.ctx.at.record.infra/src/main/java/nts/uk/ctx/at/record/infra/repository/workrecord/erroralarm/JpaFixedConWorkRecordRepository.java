package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KrcmtFixedConditionWorkRecord;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KrcmtFixedConditionWorkRecordPK;

@Stateless
public class JpaFixedConWorkRecordRepository extends JpaRepository implements  FixedConditionWorkRecordRepository {

	private final String SELECT_FROM_FIXED_CON = " SELECT c FROM KrcmtFixedConditionWorkRecord c ";
	
	private final String SELECT_FIXED_CON_BY_ALARM_ID =SELECT_FROM_FIXED_CON 
			+ " WHERE c.krcmtFixedConditionWorkRecordPK.errorAlarmCode = :errorAlarmCode ";
	
	private final String SELECT_FIXED_CON_BY_CODE = SELECT_FIXED_CON_BY_ALARM_ID
			+" AND c.krcmtFixedConditionWorkRecordPK.fixConWorkRecordNo = :fixConWorkRecordNo ";
	
	@Override
	public List<FixedConditionWorkRecord> getAllFixedConditionWorkRecord() {
		List<FixedConditionWorkRecord> data = this.queryProxy().query(SELECT_FROM_FIXED_CON,KrcmtFixedConditionWorkRecord.class)
				.getList(c->c.toDomain());
		return data;
	}

	@Override
	public List<FixedConditionWorkRecord> getAllFixedConWRByAlarmID(String errorAlarmCode) {
		List<FixedConditionWorkRecord> data = this.queryProxy().query(SELECT_FIXED_CON_BY_ALARM_ID,KrcmtFixedConditionWorkRecord.class)
				.setParameter("errorAlarmCode", errorAlarmCode)
				.getList(c->c.toDomain());
		return data;
	}

	@Override
	public Optional<FixedConditionWorkRecord> getFixedConWRByCode(String errorAlarmCode, int fixConWorkRecordNo) {
		Optional<FixedConditionWorkRecord> data = this.queryProxy().query(SELECT_FIXED_CON_BY_CODE,KrcmtFixedConditionWorkRecord.class)
				.setParameter("errorAlarmCode", errorAlarmCode)
				.setParameter("fixConWorkRecordNo", fixConWorkRecordNo)
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
				new KrcmtFixedConditionWorkRecordPK(
				newEntity.krcmtFixedConditionWorkRecordPK.errorAlarmCode,
				newEntity.krcmtFixedConditionWorkRecordPK.fixConWorkRecordNo),
				KrcmtFixedConditionWorkRecord.class).get();
		updateEntity.message = newEntity.message;
		updateEntity.useAtr = newEntity.useAtr;
		this.commandProxy().update(updateEntity);
	}

	@Override
	public void deleteFixedConWorkRecord(String errorAlarmCode, int fixConWorkRecordNo) {
		this.commandProxy().remove(KrcmtFixedConditionWorkRecord.class,new KrcmtFixedConditionWorkRecordPK(
				errorAlarmCode,fixConWorkRecordNo));
		
	}

}
