package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfDays;

/**
 * 日数チェック条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DayCheckCond implements ScheduleYearCheckCond {

    // チェックする日数
    private TypeOfDays typeOfDays;
}
