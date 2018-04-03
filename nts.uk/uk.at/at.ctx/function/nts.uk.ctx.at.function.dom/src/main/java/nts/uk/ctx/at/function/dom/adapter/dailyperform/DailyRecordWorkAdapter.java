package nts.uk.ctx.at.function.dom.adapter.dailyperform;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface DailyRecordWorkAdapter {

	DailyRecordWorkImport getByEmployeeList(String employeeId, GeneralDate baseDate , List<Integer> itemIds);
	
	
}
