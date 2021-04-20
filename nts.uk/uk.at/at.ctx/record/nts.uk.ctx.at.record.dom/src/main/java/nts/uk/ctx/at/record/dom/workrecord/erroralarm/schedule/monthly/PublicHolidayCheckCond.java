package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 所定公休日数チェック条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PublicHolidayCheckCond implements ScheduleMonCheckCond{
    // チェックする対比
    private TypeOfContrast typeOfContrast;
}
