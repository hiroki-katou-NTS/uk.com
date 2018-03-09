package nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.ConditionAlarmError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetermineErrorAlarmWorkRecordService {
	
	@Inject
	private WorkRecordCheckService workRecordCheckService;
	
	@Inject 
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;
	
	@Inject
	private ConditionAlarmError conditionAlarmError;
	
	public void insertErrorAlarm(DailyRecordWorkCommand command){
		String companyID = AppContexts.user().companyId();
		List<ErrorAlarmWorkRecord> lstErrorAlarm  = conditionAlarmError.getErAlConditons(companyID);
		if (!lstErrorAlarm.isEmpty()) {
			lstErrorAlarm.forEach(erAl -> {
				if (erAl.getErrorAlarmCondition() != null && erAl.getErrorDisplayItem() != null) {
//					Map<String, Boolean> lstSidCheck = workRecordCheckService.check(command.getWorkDate(),
//							Arrays.asList(command.getEmployeeId()), erAl.getErrorAlarmCondition());
//					if (!lstSidCheck.isEmpty() && lstSidCheck.get(command.getEmployeeId())) {
						createEmployeeDailyPerError.createEmployeeDailyPerError(companyID, command.getEmployeeId(),
								command.getWorkDate(), new ErrorAlarmWorkRecordCode(erAl.getCode().v()), Arrays.asList(erAl.getErrorDisplayItem().intValue()));
					}
//				}
			});
		}
	}

}
