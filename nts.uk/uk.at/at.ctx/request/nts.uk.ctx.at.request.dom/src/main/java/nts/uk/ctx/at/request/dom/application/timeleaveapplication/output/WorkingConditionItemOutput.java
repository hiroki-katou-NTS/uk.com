package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 時間休暇残数
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkingConditionItemOutput {

    // 60H超休の残時間
    private int subOfHoliday;

    // 介護の残日数
    private int dayOfLongTerm;

    // 介護の残時間
    private int timeOfLongTerm;

    // 子看護の残日数
    private int dayOfChildNursing;

    // 子看護の残時間
    private int timeOfChildNursing;

    // 時間代休の残時間
    private int timeOfTimeOff;

    // 時間年休の残日数
    private int dayOfAnnualLeave;

    // 時間年休の残時間
    private int timeOfAnnualLeave;

    // 時間特別休暇残数
    private TimeSpecialVacationRemaining timeSpecialVacationRemaining;

    // 残数期間
    private DatePeriod datePeriod;

}
