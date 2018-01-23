package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.List;


public interface ErrorAlarmWorkRecordPub {
	//get error by eralCheckId
		ErrorAlarmWorkRecordPubExport findByErrorAlamCheckId(String eralCheckId);
		
		//get error by list eralCheckId
		List<ErrorAlarmWorkRecordPubExport> findByListErrorAlamCheckId(List<String> listEralCheckId);
		
		void addErrorAlarmWorkRecordPub(ErrorAlarmWorkRecordPubExport errorAlarmWorkRecordPubExport);
		
		void updateErrorAlarmWorkRecordPub(ErrorAlarmWorkRecordPubExport errorAlarmWorkRecordPubExport);
		
		void deleteErrorAlarmWorkRecordPub(List<String>  listEralCheckId);
}
