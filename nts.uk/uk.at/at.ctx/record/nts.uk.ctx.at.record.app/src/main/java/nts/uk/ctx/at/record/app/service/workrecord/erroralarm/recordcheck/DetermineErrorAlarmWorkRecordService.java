package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
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
		Map<ErrorAlarmWorkRecord, Map<String, Boolean>> lstErrorAlarm = checkErrorFor(employeeID, date);
		if (!lstErrorAlarm.isEmpty()) {
			lstErrorAlarm.entrySet().forEach(erAl -> {
				if (!erAl.getValue().isEmpty() && erAl.getValue().get(employeeID)) {
					createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, employeeID, date,
							new ErrorAlarmWorkRecordCode(erAl.getKey().getCode().v()),
							Arrays.asList(erAl.getKey().getErrorDisplayItem().intValue()));

				}
			});
		}
	}

	@Override
	public Map<ErrorAlarmWorkRecord, Map<String, Boolean>> checkErrorFor(String employeeID, GeneralDate date) {
		String companyID = AppContexts.user().companyId();
		List<ErrorAlarmWorkRecord> lstErrorAlarm = conditionAlarmError.getErAlConditons(companyID);
		if (!lstErrorAlarm.isEmpty()) {
			return lstErrorAlarm.stream().collect(Collectors.toMap(erAl -> erAl, erAl -> {
				if (erAl.getErrorAlarmCondition() != null && erAl.getErrorDisplayItem() != null) {
					return new HashMap<>();
				}
				return workRecordCheckService.check(date, Arrays.asList(employeeID), erAl.getErrorAlarmCondition());
			}));
		}
		return new HashMap<>();
	}

}
