package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 時間休暇残数
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeVacationRemainingOutput {

    // 60H超休の残時間
    private int super60HRemainingTime;

    // 介護の残日数
    private int careRemainingDays;

    // 介護の残時間
    private int careRemainingTime;

    // 子看護の残日数
    private int childCareRemainingDays;

    // 子看護の残時間
    private int childCareRemainingTime;

    // 時間代休の残時間
    private int subTimeLeaveRemainingTime;

    // 時間年休の残日数
    private int annualTimeLeaveRemainingDays;

    // 時間年休の残時間
    private int annualTimeLeaveRemainingTime;

    // 時間特別休暇残数
    private List<TimeSpecialVacationRemaining> specialTimeFrames;

    // 残数期間
    private DatePeriod remainingPeriod;

}
