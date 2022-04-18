package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily.multi;

import lombok.AllArgsConstructor;
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
import java.util.Optional;

/**
 * スケジュール複数日のチェック条件
 */
@AllArgsConstructor
public class CheckScheduleMultiDaily {

    private List<String> workTypeCodes;

    private List<String> workTimeCodes;

    private Optional<AlarmListConditionValue<
            ConditionValueScheduleMultiDailyByEmployee,
            ConditionValueScheduleMultiDailyByEmployee.Context>> valueChecker;


    public Iterable<GeneralDate> check(ConditionValueScheduleMultiDailyByEmployee.Require require,
                                        String employeeId, DatePeriod period) {
        return valueChecker.isPresent()
                ? checkWithValue(require, employeeId, period)
                : checkNoValue(require, employeeId, period);
    }

    private Iterable<GeneralDate> checkWithValue(ConditionValueScheduleMultiDailyByEmployee.Require require, String employeeId, DatePeriod period) {
        return IteratorUtil.iterableFilter(period.iterate(),
            date -> {
                val c = new ConditionValueScheduleMultiDailyByEmployee.Context(
                        require, employeeId, date, workTypeCodes, workTimeCodes);
                return valueChecker.get().checkIfEnabled(c).map(result -> date);
            });
    }

    private Iterable<GeneralDate> checkNoValue(ConditionValueScheduleMultiDailyByEmployee.Require require,
                                       String employeeId, DatePeriod period) {
        return IteratorUtil.iterableFilter(period.iterate(),
                date -> {
                    val ws = require.getWorkSchedule(employeeId, date);
                    if (!ws.isPresent()) return Optional.empty();

                    // 指定勤務種類以外はチェックしない
                    if (!workTypeCodes.isEmpty()
                            && !workTypeCodes.contains(ws.get().getWorkInfo().getRecordInfo().getWorkTypeCode())) {
                        return Optional.empty();
                    }

                    // 指定就業時間帯以外はチェックしない
                    if (!workTimeCodes.isEmpty()
                            && !workTimeCodes.contains(ws.get().getWorkInfo().getRecordInfo().getWorkTimeCode())) {
                        return Optional.empty();
                    }

                    return Optional.of(date);
                });
    }

    public String name() {
        return valueChecker.map(vc -> vc.getLogic().getName())
                .orElse("連続勤務");
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
