package nts.uk.ctx.alarm.dom.byemployee.check.checkers.appapproval;

import java.util.List;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectedState;

/**
 *固定のチェック条件(社員別・申請承認) 
 */
@RequiredArgsConstructor
public enum FixedLogicAppApprovalByEmployee {

	否認(7, c -> getApplicatoinBy(c, ReflectedState.DENIAL)),
	;
	
    public final int value;

    private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;
    
    /**
     * チェックする
     */
    public Iterable<AlarmRecordByEmployee> check(
    		FixedLogicAppApprovalByEmployee.RequireCheck require,
            String employeeId,
            String message) {
    	
//        val context = new Context(require, employeeId, checkingPeriod.calculatePeriod(require, employeeId), message);
    	val context = new Context(require, employeeId, DatePeriod.oneDay(GeneralDate.today()), message);
    	
        return logic.apply(context);
    }
    
    private static Iterable<AlarmRecordByEmployee> getApplicatoinBy(Context context, ReflectedState state) {
    	return () -> context.period.datesBetween().stream()
    			.map(date -> context.getRequire().getApplicationBy(context.employeeId, date, state))
    			.flatMap(List::stream)
    			.map(application -> context.alarm(application.getAppDate().getApplicationDate(), state))
    			.iterator()
    			;
    }
    
    private String getAlarmCondition() {
        return "アラーム条件";
    }
    
    @Value
    private class Context {
        RequireCheck require;
        String employeeId;
        DatePeriod period;
        String message;
        
        public AlarmRecordByEmployee alarm(GeneralDate date, ReflectedState state) {
        	return new AlarmRecordByEmployee(
        			employeeId, 
        			date.toString(), 
        			AlarmListCategoryByEmployee.APPLICATION_APPROVAL, 
        			state.name, 
        			getAlarmCondition(), 
        			message);
        }
        
    }
    
    public interface RequireCheck{
    	List<Application> getApplicationBy(String employeeId, GeneralDate targetDate, ReflectedState states);
    }
}
