package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;

public interface ErAlCheckService {
	
	public void checkAndInsert(String employeeID, GeneralDate date);

	public List<EmployeeDailyPerError> checkErrorFor(String employeeID, GeneralDate date);
	
	public List<EmployeeDailyPerError> checkErrorFor(String companyId, String employeeID, GeneralDate date, 
			ErrorAlarmWorkRecord erAl);
	
	public List<EmployeeDailyPerError> checkErrorFor(String companyId, String employeeID, GeneralDate date, 
			ErrorAlarmWorkRecord erAl, IntegrationOfDaily record);
	
	public void createEmployeeDailyPerError(List<EmployeeDailyPerError> errors);
}
