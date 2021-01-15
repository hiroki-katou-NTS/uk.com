package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.annual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.monthly.TypeOfTime;

/**
 * 時間チェック条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TimeCheckCond implements ScheduleYearCheckCond {
    // チェックする時間
    private TypeOfTime typeOfTime;
}
