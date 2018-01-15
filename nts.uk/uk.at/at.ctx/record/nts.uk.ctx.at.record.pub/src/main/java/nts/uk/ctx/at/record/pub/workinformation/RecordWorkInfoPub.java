package nts.uk.ctx.at.record.pub.workinformation;

import nts.arc.time.GeneralDate;

public interface RecordWorkInfoPub {
	
	RecordWorkInfoPubExport getRecordWorkInfo(String employeeId, GeneralDate ymd);

}
