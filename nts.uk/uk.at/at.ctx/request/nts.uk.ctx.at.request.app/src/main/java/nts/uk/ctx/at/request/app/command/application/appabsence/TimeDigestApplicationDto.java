package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@Data
@AllArgsConstructor
public class TimeDigestApplicationDto {
    
    private Integer overtime60H;
    
    private Integer nursingTime;
    
    private Integer childTime;
    
    private Integer timeOff;
    
    private Integer timeSpecialVacation;
    
    private Integer timeAnualLeave;
    
    private Integer specialVacationFrameNO;

    public TimeDigestApplication toDomain() {
        return new TimeDigestApplication(
                overtime60H == null ? null : new AttendanceTime(overtime60H), 
                nursingTime == null ? null : new AttendanceTime(nursingTime), 
                childTime == null ? null : new AttendanceTime(childTime),
                timeOff == null ? null : new AttendanceTime(timeOff), 
                timeSpecialVacation == null ? null : new AttendanceTime(timeSpecialVacation), 
                timeAnualLeave == null ? null : new AttendanceTime(timeAnualLeave), 
                Optional.ofNullable(specialVacationFrameNO));
    }
}
