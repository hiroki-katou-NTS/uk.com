package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

import java.util.Optional;

@Data
@AllArgsConstructor
public class TimeDigestDto {

    /**
     * 60H超休
     */
    private Integer overtime60H;

    /**
     * 介護時間
     */
    private Integer nursingTime;

    /**
     * 子の看護時間
     */
    private Integer childTime;

    /**
     * 時間代休時間
     */
    private Integer timeOff;

    /**
     * 時間特別休暇
     */
    private Integer timeSpecialVacation;

    /**
     * 時間年休時間
     */
    private Integer timeAnualLeave;

    /**
     * 特別休暇枠NO
     */
    private Integer specialHdFrameNo;

    public TimeDigestApplication toDomain() {

        return new TimeDigestApplication(
            new AttendanceTime(this.getOvertime60H()),
            new AttendanceTime(this.getNursingTime()),
            new AttendanceTime(this.getChildTime()),
            new AttendanceTime(this.getTimeOff()),
            new AttendanceTime(this.getTimeSpecialVacation()),
            new AttendanceTime(this.getTimeAnualLeave()),
            this.getSpecialHdFrameNo() == null ? Optional.empty() : Optional.of(this.getSpecialHdFrameNo())
        );
    }

}
