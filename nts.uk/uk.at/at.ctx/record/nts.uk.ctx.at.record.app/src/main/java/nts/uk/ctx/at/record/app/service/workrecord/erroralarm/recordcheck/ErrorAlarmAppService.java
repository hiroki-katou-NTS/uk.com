package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;

public interface ErrorAlarmAppService {

	void checkAndInsert(String employeeID, GeneralDate date, DailyRecordDto record);
}
