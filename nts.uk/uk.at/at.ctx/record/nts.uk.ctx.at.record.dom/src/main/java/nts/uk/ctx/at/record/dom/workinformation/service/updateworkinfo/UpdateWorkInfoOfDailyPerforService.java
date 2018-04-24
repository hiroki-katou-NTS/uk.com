package nts.uk.ctx.at.record.dom.workinformation.service.updateworkinfo;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

public interface UpdateWorkInfoOfDailyPerforService {
	public void updateWorkInfoOfDailyPerforService(String companyId, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance);
}
