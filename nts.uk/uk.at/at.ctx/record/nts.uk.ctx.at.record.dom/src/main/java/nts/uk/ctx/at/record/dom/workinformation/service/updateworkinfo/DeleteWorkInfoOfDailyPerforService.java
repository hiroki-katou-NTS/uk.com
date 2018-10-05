package nts.uk.ctx.at.record.dom.workinformation.service.updateworkinfo;

import nts.arc.time.GeneralDate;

/*
 * 日別実績の勤務情報を削除する
 */
public interface DeleteWorkInfoOfDailyPerforService {

	public void deleteWorkInfoOfDailyPerforService(String employeeID, GeneralDate processingDate);
}
