package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 時間休暇残数
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeVacationRemainingOutput {

    // 60H超休の残時間
    private int super60HRemainingTime = 0;

    // 介護の残日数
    private double careRemainingDays = 0;

    // 介護の残時間
    private int careRemainingTime = 0;

    // 子看護の残日数
    private double childCareRemainingDays = 0;

    // 子看護の残時間
    private int childCareRemainingTime = 0;

    // 時間代休の残時間
    private int subTimeLeaveRemainingTime = 0;

    // 時間年休の残日数
    private double annualTimeLeaveRemainingDays = 0;

    // 時間年休の残時間
    private int annualTimeLeaveRemainingTime = 0;

    // 時間特別休暇残数
    private List<TimeSpecialVacationRemaining> specialTimeFrames = new ArrayList<>();

    // 残数期間
    private DatePeriod remainingPeriod;

    // 付与年月日
    private Optional<GeneralDate> grantDate = Optional.empty();

    // 付与日数
    private Optional<Double> grantedDays = Optional.empty();

}
