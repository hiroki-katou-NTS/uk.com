package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition;

import java.util.List;


public interface WorkRecordExtraConPub {
	List<WorkRecordExtraConPubExport> getAllWorkRecordExtraConByListID(List<String> listErrorAlarmID );
	
	WorkRecordExtraConPubExport getWorkRecordExtraConById(String errorAlarmCheckID);
}
