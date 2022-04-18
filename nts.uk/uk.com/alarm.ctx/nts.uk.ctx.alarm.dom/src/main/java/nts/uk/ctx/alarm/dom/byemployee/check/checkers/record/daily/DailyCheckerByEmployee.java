package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;

import java.util.Arrays;
import java.util.List;

/**
 * アラームリストのチェック条件(社員別・日次)
 */
@AllArgsConstructor
@Getter
public class DailyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    /** 会社ID */
    private final String companyId;

    /** コード */
    private final AlarmListCheckerCode code;

    /** エラーアラームのチェック条件 */
    private CheckErrorAlarmDaily errorAlarm;

    /** 固定のチェック条件 */
    private List<FixedLogicSetting<FixedLogicDailyByEmployee>> fixedLogics;
    
	/** 条件値のチェック条件*/
    private List<AlarmListConditionValue<
    		ConditionValueDailyByEmployee, 
    		ConditionValueDailyByEmployee.Context>> conditionValues;

    /**
     * チェックする
     * @param require
     * @param context
     * @return
     */
    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {

        String employeeId = context.getTargetEmployeeId();
        val period = context.getCheckingPeriod().getDaily().calculatePeriod(require, employeeId);

        List<Iterable<AlarmRecordByEmployee>> alarmRecords = Arrays.asList(
                errorAlarm.check(require, employeeId, period),
                checkFixedLogics(require, employeeId, period)
        );

        return IteratorUtil.flatten(alarmRecords);
    }

    /**
     * 固定チェック条件
     * @param require
     * @param employeeId
     * @param period
     * @return
     */
    private Iterable<AlarmRecordByEmployee> checkFixedLogics(Require require, String employeeId, DatePeriod period) {
        return IteratorUtil.iterableFlatten(
                fixedLogics,
                f -> f.checkIfEnabled((logic, message) -> logic.check(require, employeeId, period, message)));
    }

    public interface RequireCheck extends
            CheckingPeriodDaily.Require,
            CheckErrorAlarmDaily.RequireCheck,
            FixedLogicDailyByEmployee.RequireCheck,
            ConditionValueDailyByEmployee.Require{

    }
}
