package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;

public interface ErAlCheckService {
	
	public void checkAndInsert(String employeeID, GeneralDate date);

	public List<EmployeeDailyPerError> checkErrorFor(String employeeID, GeneralDate date);
	
	public void createEmployeeDailyPerError(List<EmployeeDailyPerError> errors);
}
