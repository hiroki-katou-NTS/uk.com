package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily.multi;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueExpression;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;

import java.util.List;
import java.util.Optional;

/**
 * スケジュール複数日のチェック条件
 */
@AllArgsConstructor
public class CheckScheduleMultiDaily {

    CheckScheduleMultiDailyType type;

    /** 使用するか */
    boolean enabled;

    /** 勤務種類 */
    private List<String> workTypeCodes;

    /** 就業時間帯 */
    private List<String> workTimeCodes;

    /** 条件式 */
    private Optional<ConditionValueExpression> expression;

    /** アラームメッセージ */
    AlarmListAlarmMessage alarmMessage;

    /** カウントすべき日付を取得 */
    public Iterable<GeneralDate> getDateForCounter(Require require,
                                        String employeeId, DatePeriod period) {

        return IteratorUtil.iterableFilter(
                period.iterate(),
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

                    // 総労働時間（予定）の条件値チェック
                    if (expression.isPresent()){
                        Double actualValue = ws.get().getOptAttendanceTime().get()
                                .getActualWorkingTimeOfDaily()
                                .getTotalWorkingTime()
                                .getTotalTime()
                                .v().doubleValue();
                        if(!expression.get().matches(actualValue)){
                            return Optional.empty();
                        }
                    }

                    return Optional.of(date);
                });
    }

    public String name() {
        return type.getName();
    }

    public interface Require {
        Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date);
    }
}
