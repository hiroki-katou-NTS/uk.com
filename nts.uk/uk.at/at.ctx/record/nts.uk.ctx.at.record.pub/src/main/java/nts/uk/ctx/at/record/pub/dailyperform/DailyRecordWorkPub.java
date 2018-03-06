package nts.uk.ctx.at.record.pub.dailyperform;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface DailyRecordWorkPub {
	DailyRecordWorkExport getByEmployee(String employeeId, GeneralDate baseDate, List<Integer> itemIds);
}
