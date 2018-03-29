package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service.ErAlCheckService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetermineErrorAlarmWorkRecordService implements ErAlCheckService, ErrorAlarmAppService {

	@Inject
	private ErAlWorkRecordCheckService workRecordCheckService;

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	// @Inject
	// private ConditionAlarmError conditionAlarmError;

	@Override
	public void checkAndInsert(String employeeID, GeneralDate date) {
		checkAndInsert(employeeID, date, null);
	}

	@Override
	public List<EmployeeDailyPerError> checkErrorFor(String employeeID, GeneralDate date) {
		String companyID = AppContexts.user().companyId();
		return checkError(employeeID, date, companyID, null);
	}

	private List<EmployeeDailyPerError> checkError(String employeeID, GeneralDate date, String comapanyId,
			DailyRecordDto record) {
		return workRecordCheckService.checkWith(comapanyId, date, Arrays.asList(employeeID),
				record == null ? null : Arrays.asList(record));
	}

	@Override
	public void checkAndInsert(String employeeID, GeneralDate date, DailyRecordDto record) {
		String companyID = AppContexts.user().companyId();
		List<EmployeeDailyPerError> lstErrorAlarm = checkError(employeeID, date, companyID, record);
		if (!lstErrorAlarm.isEmpty()) {
			lstErrorAlarm.stream().forEach(erAl -> {
				createEmployeeDailyPerError.checkAndInsert(erAl);
			});
		}
	}
}
