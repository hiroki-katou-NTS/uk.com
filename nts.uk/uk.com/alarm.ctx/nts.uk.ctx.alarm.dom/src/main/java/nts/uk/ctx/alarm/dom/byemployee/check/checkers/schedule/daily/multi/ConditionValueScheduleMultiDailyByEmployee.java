package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily.multi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 条件値チェック（社員別・スケジュール複数日）
 */
@RequiredArgsConstructor
public enum ConditionValueScheduleMultiDailyByEmployee implements ConditionValueLogic<ConditionValueScheduleMultiDailyByEmployee.Context> {
    連続時間_予定時間(1, "連続時間:予定時間", c -> {
        val ws = c.require.getWorkSchedule(c.employeeId, c.date).orElse(null);
        if (ws == null) return null;

        // 指定勤務種類以外はチェックしない
        if (!c.workTypeCodes.isEmpty()
            && !c.workTypeCodes.contains(ws.getWorkInfo().getRecordInfo().getWorkTypeCode())) {
            return null;
        }

        // 指定就業時間帯以外はチェックしない
        if (!c.workTimeCodes.isEmpty()
                && !c.workTimeCodes.contains(ws.getWorkInfo().getRecordInfo().getWorkTimeCode())) {
            return null;
        }

        return ws.getOptAttendanceTime()
                .map(at -> Double.valueOf(at
                        .getActualWorkingTimeOfDaily()
                        .getTotalWorkingTime()
                        .getTotalTime().v()))
                .orElse(null);
    }),
    ;

    public final int value;

    /** 項目名 */
    @Getter
    private final String name;

    private final Function<Context, Double> getValue;

    @Override
    public Double getValue(Context context) {
        return getValue.apply(context);
    }

    @Value
    public static class Context implements ConditionValueContext {
        Require require;
        String employeeId;
        GeneralDate date;

        private List<String> workTypeCodes;
        private List<String> workTimeCodes;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.SCHEDULE_MULTI_DAY;
        }

        @Override
        public DateInfo getDateInfo() {
            return new DateInfo(date);
        }
    }

    public interface Require {
        Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date);
    }
}
