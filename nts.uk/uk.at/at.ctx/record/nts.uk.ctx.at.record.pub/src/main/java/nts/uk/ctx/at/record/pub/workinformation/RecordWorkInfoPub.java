package nts.uk.ctx.at.record.pub.workinformation;

import nts.arc.time.GeneralDate;

public interface RecordWorkInfoPub {

	/**
	 * RequestList5
	 * 
	 * @param employeeId 社員ID
	 * @param ymd　日付
	 * @return　RecordWorkInfoPubExport
	 */
	RecordWorkInfoPubExport getRecordWorkInfo(String employeeId, GeneralDate ymd);

}
