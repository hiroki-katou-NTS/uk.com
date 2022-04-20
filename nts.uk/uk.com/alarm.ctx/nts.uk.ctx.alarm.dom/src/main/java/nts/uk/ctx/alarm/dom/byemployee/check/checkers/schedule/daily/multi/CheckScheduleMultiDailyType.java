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
 * スケジュール複数日チェック種類
 */
@RequiredArgsConstructor
public enum CheckScheduleMultiDailyType {
    連続時間_予定時間(1, "連続時間:予定時間"),
    連続勤務(2, "連続勤務"),
    連続時間帯(3, "連続時間帯");

    public final int value;

    /** 項目名 */
    @Getter
    private final String name;

}