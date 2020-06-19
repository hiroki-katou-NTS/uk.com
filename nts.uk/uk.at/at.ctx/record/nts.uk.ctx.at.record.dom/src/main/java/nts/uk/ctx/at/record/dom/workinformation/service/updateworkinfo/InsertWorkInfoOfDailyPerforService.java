package nts.uk.ctx.at.record.dom.workinformation.service.updateworkinfo;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkInfoOfDailyPerformance;

/**
 * 日別実績の勤務情報を登録する
 * 
 * @author nampt
 *
 */
public interface InsertWorkInfoOfDailyPerforService {

	public void updateWorkInfoOfDailyPerforService(String companyId, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformanceUpdate);

}
