package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfTime;

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
