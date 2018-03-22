package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;

public interface ErAlCheckService {
	
	public void checkAndInsert(String employeeID, GeneralDate date);

	public Map<ErrorAlarmWorkRecord, Map<String, Boolean>> checkErrorFor(String employeeID, GeneralDate date);
}
