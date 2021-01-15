package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 日数チェック条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DayCheckCond implements ScheduleMonCheckCond {

    // チェックする日数
    private TypeOfDays typeOfDays;
}
