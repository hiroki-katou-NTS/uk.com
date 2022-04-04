package nts.uk.ctx.alarm.dom.byemployee.check.checkers.master;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.shared.dom.workingcondition.service.GetNotExistWeekDayWorkType;

/**
 *固定のチェック条件(社員別・マスタ) 
 */
@RequiredArgsConstructor
public enum FixLogicMasterByEmployee {
	
	平日時勤務種類確認(3, c -> checkWeekdayWorkType(c)),
	
	;
	
    public final int value;

    private final Function<Context, List<AlarmRecordByEmployee>> logic;

	public List<AlarmRecordByEmployee> check(
			FixLogicMasterByEmployee.RequireCheck require, 
			String employeeId, 
			String message) {
		
		val contex = new Context(require, employeeId, message);
		return logic.apply(contex);
	}
	
	private static List<AlarmRecordByEmployee> checkWeekdayWorkType(Context context){
		val checkResults = GetNotExistWeekDayWorkType.get(context.require, context.employeeId);
		return checkResults
				.entrySet()
				.stream()
				.map(pWithw -> context.alarm(pWithw.getKey()))
				.collect(Collectors.toList())
				;
	}
    
    private String getName() {
        return "チェック項目名";
    }

    private String getAlarmCondition() {
        return "アラーム条件";
    }
    
    @Value
    private class Context {
        RequireCheck require;
        String employeeId;
        String message;

        public AlarmRecordByEmployee alarm(DatePeriod period) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    period.toString(),
                    AlarmListCategoryByEmployee.MASTER,
                    getName(),
                    getAlarmCondition(),
                    message);
        }
        
        public AlarmRecordByEmployee alarm(GeneralDate date) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    date.toString(),
                    AlarmListCategoryByEmployee.MASTER,
                    getName(),
                    getAlarmCondition(),
                    message);
        }
    }
    
    public interface RequireCheck extends GetNotExistWeekDayWorkType.Require{

    }
}
