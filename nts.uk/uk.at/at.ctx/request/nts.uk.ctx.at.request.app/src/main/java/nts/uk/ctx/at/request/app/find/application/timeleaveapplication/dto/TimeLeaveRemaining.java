package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeSpecialVacationRemaining;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeVacationRemainingOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * 時間休暇残数
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeLeaveRemaining {
    // 60H超休の残時間
    private Integer super60HRemainingTime = 0;

    // 介護の残日数
    private Double careRemainingDays = 0.0;

    // 介護の残時間
    private Integer careRemainingTime = 0;

    // 子看護の残日数
    private Double childCareRemainingDays = 0.0;

    // 子看護の残時間
    private Integer childCareRemainingTime = 0;

    // 時間代休の残時間
    private Integer subTimeLeaveRemainingTime = 0;

    // 時間年休の残日数
    private Double annualTimeLeaveRemainingDays = 0.0;

    // 時間年休の残時間
    private Integer annualTimeLeaveRemainingTime = 0;

    // 時間特別休暇残数
    private List<TimeSpecialVacationRemaining> specialTimeFrames = new ArrayList<>();

    // 残数期間
    private GeneralDate remainingStart;
    private GeneralDate remainingEnd;

    public static TimeVacationRemainingOutput setDataOutput(TimeLeaveRemaining dto) {
        return new TimeVacationRemainingOutput(
            dto.super60HRemainingTime,
            dto.careRemainingDays,
            dto.careRemainingTime,
            dto.childCareRemainingDays,
            dto.childCareRemainingTime,
            dto.subTimeLeaveRemainingTime,
            dto.annualTimeLeaveRemainingDays,
            dto.annualTimeLeaveRemainingTime,
            dto.specialTimeFrames,
            new DatePeriod(dto.remainingStart, dto.remainingEnd)
        );
    }

    public static TimeLeaveRemaining fromOutput(TimeVacationRemainingOutput domain) {
        return new TimeLeaveRemaining(
                domain.getSuper60HRemainingTime(),
                domain.getCareRemainingDays(),
                domain.getCareRemainingTime(),
                domain.getChildCareRemainingDays(),
                domain.getChildCareRemainingTime(),
                domain.getSubTimeLeaveRemainingTime(),
                domain.getAnnualTimeLeaveRemainingDays(),
                domain.getAnnualTimeLeaveRemainingTime(),
                domain.getSpecialTimeFrames(),
                domain.getRemainingPeriod().start(),
                domain.getRemainingPeriod().end()
        );
    }
}
