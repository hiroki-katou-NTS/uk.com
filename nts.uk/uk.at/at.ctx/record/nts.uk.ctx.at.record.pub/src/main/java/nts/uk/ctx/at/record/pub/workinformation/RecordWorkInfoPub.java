package nts.uk.ctx.at.record.pub.workinformation;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface RecordWorkInfoPub {
	
	RecordWorkInfoPubExport getRecordWorkInfo(String employeeId, GeneralDate ymd);

	InfoCheckNotRegisterPubExport getInfoCheckNotRegister(String employeeId, GeneralDate ymd);
}
