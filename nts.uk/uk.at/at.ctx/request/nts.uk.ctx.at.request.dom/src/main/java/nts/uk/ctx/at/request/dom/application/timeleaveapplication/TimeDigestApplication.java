package nts.uk.ctx.at.request.dom.application.timeleaveapplication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;

import java.util.Optional;

/**
 * 時間消化申請
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeDigestApplication {

    /**
     * 60H超休
     */
    private AttendanceTime sixtyHOvertime = new AttendanceTime(0);

    /**
     * 介護時間
     */
    private AttendanceTime nursingTime = new AttendanceTime(0);

    /**
     * 子の看護時間
     */
    private AttendanceTime childNursingTime = new AttendanceTime(0);

    /**
     * 時間代休時間
     */
    private AttendanceTime hoursOfSubHoliday = new AttendanceTime(0);

    /**
     * 時間年休時間
     */
    private AttendanceTime hoursOfHoliday = new AttendanceTime(0);

    /**
     * 時間特別休暇
     */
    private AttendanceTime TimeSpecialVacation = new AttendanceTime(0);

    /**
     * 特別休暇枠NO
     */
    private Optional<SpecialHdFrameNo> specialHdFrameNo =  Optional.empty();

}
