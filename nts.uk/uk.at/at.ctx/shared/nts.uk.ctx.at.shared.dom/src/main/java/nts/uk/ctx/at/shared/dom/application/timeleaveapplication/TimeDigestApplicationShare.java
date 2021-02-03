package nts.uk.ctx.at.shared.dom.application.timeleaveapplication;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * @author anhnm
 * 時間消化申請
 *
 */
@Data
@NoArgsConstructor
public class TimeDigestApplicationShare {

    /**
     * 60H超休
     */
    private AttendanceTime overtime60H;

    /**
     * 介護時間
     */
    private AttendanceTime nursingTime;

    /**
     * 子の看護時間
     */
    private AttendanceTime childTime;

    /**
     * 時間代休時間
     */
    private AttendanceTime timeOff;

    /**
     * 時間特別休暇
     */
    private AttendanceTime timeSpecialVacation;

    /**
     * 時間年休時間
     */
    private AttendanceTime timeAnnualLeave;

    /**
     * 特別休暇枠NO
     */
    private Optional<Integer> specialVacationFrameNO;
    
    public TimeDigestApplicationShare(AttendanceTime overtime60H, 
            AttendanceTime nursingTime, 
            AttendanceTime childTime, 
            AttendanceTime timeOff, 
            AttendanceTime timeSpecialVacation, 
            AttendanceTime timeAnnualLeave,
            Optional<Integer> specialVacationFrameNO) {
        this.overtime60H = overtime60H;
        this.nursingTime = nursingTime;
        this.childTime = childTime;
        this.timeOff = timeOff;
        this.timeSpecialVacation = timeSpecialVacation;
        this.timeAnnualLeave = timeAnnualLeave;
        this.specialVacationFrameNO = specialVacationFrameNO == null ? Optional.empty() : specialVacationFrameNO;
    }
}
