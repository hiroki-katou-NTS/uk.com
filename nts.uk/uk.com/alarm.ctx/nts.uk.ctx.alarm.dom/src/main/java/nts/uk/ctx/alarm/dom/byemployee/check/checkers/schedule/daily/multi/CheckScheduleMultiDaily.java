package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily.multi;

import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueExpression;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;

import java.util.List;

/**
 * スケジュール複数日のチェック条件
 */
public class CheckScheduleMultiDaily {

    private List<String> workTypeCodes;

    private List<String> workTimeCodes;

    private AlarmListConditionValue<
            ConditionValueScheduleMultiDailyByEmployee,
            ConditionValueScheduleMultiDailyByEmployee.Context> valueChecker;

    public Iterable<GeneralDate> check(AlarmListCheckerByEmployee.Require require, String employeeId, DatePeriod period) {
        return IteratorUtil.iterableFilter(period.iterate(),
            date -> {
                val c = new ConditionValueScheduleMultiDailyByEmployee.Context(
                        require, employeeId, date, workTypeCodes, workTimeCodes);
                return valueChecker.checkIfEnabled(c).map(result -> date);
            });
    }

    public String name() {
        return valueChecker.getLogic().getName();
    }

    @Value
    public static class Context implements ConditionValueContext {
        ConditionValueScheduleMultiDailyByEmployee.Require require;
        String employeeId;
        GeneralDate date;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.SCHEDULE_MULTI_DAY;
        }

        @Override
        public String getEmployeeId() {
            return employeeId;
        }

        @Override
        public DateInfo getDateInfo() {
            // 連続の場合ってどうなる？
            return new DateInfo(date);
        }
    }
}
