package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.List;


public interface FixedConWorkRecordPub {

	/**
	 * get all fixed condition work record by list errorAlarmID
	 * @return
	 */
	List<FixedConWorkRecordPubExport> getAllFixedConWorkRecordByListID(List<String> listErrorAlarmID);

	/**
	 * get fixed condition work record by dailyAlarmConID and fixConWorkRecordNo
	 * @param errorAlarmID
	 * @param fixConWorkRecordNo
	 * @return
	 */
	FixedConWorkRecordPubExport getFixedConWRByCode(String errorAlarmID);
	
	void addFixedConWorkRecordPub(FixedConWorkRecordPubExport fixedConWorkRecordPubExport);
	
	void updateFixedConWorkRecordPub(FixedConWorkRecordPubExport fixedConWorkRecordPubExport);
	
	void deleteFixedConWorkRecordPub(List<String> errorAlarmID);
}
