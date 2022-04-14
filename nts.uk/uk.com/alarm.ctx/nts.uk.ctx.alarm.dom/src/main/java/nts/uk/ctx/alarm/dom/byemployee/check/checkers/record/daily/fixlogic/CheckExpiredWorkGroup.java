package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily.fixlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.CheckWorkExpirationDateService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;

/**
 *応援作業時間帯の作業グループが期限切れではないかチェックする 
 */
public class CheckExpiredWorkGroup {
	
	public static Iterable<AlarmRecordByEmployee> check(Require require, String employeeId, GeneralDate date) {
		val ouenTimeSheet = require.getIntegrationOfDailyRecord(employeeId, date)
													  .map(iod -> iod.getOuenTimeSheet());
		
		List<AlarmRecordByEmployee> errors = new ArrayList<>();
		if(ouenTimeSheet.isPresent()) {
			ouenTimeSheet.get().forEach(timeSheet ->{
				timeSheet.getWorkContent().getWork().ifPresent(workGroup ->{
					errors.addAll(getErrors(require, employeeId, date, workGroup));
				});
			});
		}
		return () -> errors.iterator();
	}
	
	private static List<AlarmRecordByEmployee> getErrors(Require require, String employeeId, GeneralDate date, WorkGroup workGroup){
		Map<Integer, Optional<WorkCode>> groupsCode = new HashMap<>();
		groupsCode.put(1, Optional.of(workGroup.getWorkCD1()));
		groupsCode.put(2, workGroup.getWorkCD2());
		groupsCode.put(3, workGroup.getWorkCD3());
		groupsCode.put(4, workGroup.getWorkCD4());
		groupsCode.put(5, workGroup.getWorkCD5());
		return groupsCode.entrySet().stream()
				.filter(group -> isError(require, date, group.getKey(), group.getValue()))
				.map(errorGroup -> createError(employeeId, date, errorGroup.getKey(), errorGroup.getValue().get()))
				.collect(Collectors.toList());
	}
	
	private static boolean isError(Require require, GeneralDate date, int taskFrameNo, Optional<WorkCode> code) {
		try {
			//このロジックを使いたいが、エラーがあった時はBusinessExceptionでしか返さないのでtry-catch使う。
			CheckWorkExpirationDateService.check(require, date, new TaskFrameNo(taskFrameNo), code);
			return false;
		}
		catch(BusinessException e) {
			return true;
		}
	}
	
	private static AlarmRecordByEmployee createError(String employeeId, GeneralDate date, int taskFrameNo, WorkCode code) {
		return new AlarmRecordByEmployee(
				employeeId, 
				new DateInfo(date), 
				AlarmListCategoryByEmployee.RECORD_DAILY, 
				"いいいい", 
				"うううう", 
				"ええええ", 
				new AlarmListAlarmMessage("おおおお"));
	}
	
	public interface Require extends CheckWorkExpirationDateService.Require{
		Optional<IntegrationOfDaily> getIntegrationOfDailyRecord(String employeeId, GeneralDate date);
	}

}
