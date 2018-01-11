package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;
import java.util.Optional;

public interface FixedConditionWorkRecordRepository {
	/**
	 * get all fixed condition work record
	 * @return
	 */
	List<FixedConditionWorkRecord> getAllFixedConditionWorkRecord();
	/**
	 * get all fixed condition work record by daily alarm condition code
	 * @param dailyAlarmConID
	 * @return
	 */
	List<FixedConditionWorkRecord> getAllFixedConWRByAlarmID(String errorAlarmCode);
	/**
	 * get fixed condition work record by dailyAlarmConID and fixConWorkRecordNo
	 * @param dailyAlarmConID
	 * @param fixConWorkRecordNo
	 * @return
	 */
	Optional<FixedConditionWorkRecord> getFixedConWRByCode(String errorAlarmCode,int fixConWorkRecordNo);
	/**
	 * add fixed Condition Work Record
	 * @param fixedConditionWorkRecord
	 */
	void addFixedConWorkRecord (FixedConditionWorkRecord fixedConditionWorkRecord);
	/**
	 * update fixed Condition Work Record
	 * @param fixedConditionWorkRecord
	 */
	void updateFixedConWorkRecord (FixedConditionWorkRecord fixedConditionWorkRecord);
	
	/**
	 * delete fixed condition work record
	 * @param dailyAlarmConID
	 * @param fixConWorkRecordNo
	 */
	void deleteFixedConWorkRecord (String errorAlarmCode,int fixConWorkRecordNo);
	
}
