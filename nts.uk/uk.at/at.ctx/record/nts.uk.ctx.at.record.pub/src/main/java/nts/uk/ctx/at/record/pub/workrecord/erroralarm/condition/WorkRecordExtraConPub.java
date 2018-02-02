package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition;

import java.util.List;



public interface WorkRecordExtraConPub {
	List<WorkRecordExtraConPubExport> getAllWorkRecordExtraConByListID(List<String> listErrorAlarmID );
	
	WorkRecordExtraConPubExport getWorkRecordExtraConById(String errorAlarmCheckID);
	
	void addWorkRecordExtraConPub(WorkRecordExtraConPubExport workRecordExtraConPubExport);
	
	void updateWorkRecordExtraConPub(WorkRecordExtraConPubExport workRecordExtraConPubExport);
	
	void deleteWorkRecordExtraConPub(List<String> errorAlarmCheckID);
	
	List<String> checkUpdateListErAl(List<String> listErrorAlarmCheckID,List<WorkRecordExtraConPubExport> listErroAlarm);
	
	List<String> addNewListErAl(List<WorkRecordExtraConPubExport> listErroAlarm);
	
}
