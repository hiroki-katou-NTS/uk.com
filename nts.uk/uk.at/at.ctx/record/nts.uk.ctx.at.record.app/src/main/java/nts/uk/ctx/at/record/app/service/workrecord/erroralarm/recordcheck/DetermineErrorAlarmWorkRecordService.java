package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.ConditionAlarmError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service.ErAlCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetermineErrorAlarmWorkRecordService implements ErAlCheckService {

	@Inject
	private ErAlWorkRecordCheckService workRecordCheckService;

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	@Inject
	private ConditionAlarmError conditionAlarmError;

	@Override
	public void checkAndInsert(String employeeID, GeneralDate date) {
		String companyID = AppContexts.user().companyId();
		Map<ErrorAlarmWorkRecord, Map<String, Boolean>> lstErrorAlarm = checkError(employeeID, date, companyID);
		if (!lstErrorAlarm.isEmpty()) {
			lstErrorAlarm.entrySet().forEach(erAl -> {
				if (!erAl.getValue().isEmpty() && isError(erAl.getValue().get(employeeID) && erAl.getKey().getErrorDisplayItem() != null)) {
					createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, date,
							new ErrorAlarmWorkRecordCode(erAl.getKey().getCode().v()),
							Arrays.asList(erAl.getKey().getErrorDisplayItem().intValue()));

				}
			});
		}
	}

	@Override
	public List<EmployeeDailyPerError> checkErrorFor(String employeeID, GeneralDate date) {
		String companyID = AppContexts.user().companyId();
		return checkError(employeeID, date, companyID).entrySet().stream()
				.filter(ae -> !ae.getValue().isEmpty() && isError(ae.getValue().get(employeeID))).map(ae -> {
					return new EmployeeDailyPerError(companyID, employeeID, date,
							new ErrorAlarmWorkRecordCode(ae.getKey().getCode().v()),
							Arrays.asList(ae.getKey().getErrorDisplayItem().intValue()));
				}).collect(Collectors.toList());
	}
	
	@Override
	public void createEmployeeDailyPerError(List<EmployeeDailyPerError> errors) {
		for(EmployeeDailyPerError error : errors) {
			if(error.getAttendanceItemList().get(0) != null) {
				//String prefix = error.getErrorAlarmWorkRecordCode().v().substring(0, 1);
				//if(prefix.equals("D") || prefix.equals("S")) {
					createEmployeeDailyPerError.createEmployeeDailyPerError(error);
				//}
			}
		}
	}

	private Boolean isError(Boolean erAl) {
		return erAl != null && erAl;
	}

	private Map<ErrorAlarmWorkRecord, Map<String, Boolean>> checkError(String employeeID, GeneralDate date,
			String comapanyId) {
		List<ErrorAlarmWorkRecord> lstErrorAlarm = conditionAlarmError.getErAlConditons(comapanyId);
		if (!lstErrorAlarm.isEmpty()) {
			return lstErrorAlarm.stream().collect(Collectors.toMap(erAl -> erAl, erAl -> {
				if (erAl.getErrorAlarmCondition() == null || erAl.getErrorDisplayItem() == null) {
					return new HashMap<>();
				}
				return workRecordCheckService.check(date, Arrays.asList(employeeID), erAl.getErrorAlarmCondition());
			}));
		}
		return new HashMap<>();
	}

	@Override
	public List<EmployeeDailyPerError> checkErrorFor(String companyId, String employeeID, GeneralDate date, ErrorAlarmWorkRecord erAl) {
		if (erAl.getErrorAlarmCondition() == null || erAl.getErrorDisplayItem() == null) {
			return new ArrayList<>();
		}
		return workRecordCheckService.check(date, Arrays.asList(employeeID), erAl.getErrorAlarmCondition()).entrySet().stream()
				.filter(ae -> isError(ae.getValue())).map(ae -> {
					return new EmployeeDailyPerError(companyId, employeeID, date,
							erAl.getCode(),
							Arrays.asList(erAl.getErrorDisplayItem().intValue()));
				}).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeDailyPerError> checkErrorFor(String companyId, String employeeID, GeneralDate date, ErrorAlarmWorkRecord erAl,
			IntegrationOfDaily record) {
		// TODO Auto-generated method stub
		return null;
	}
}
