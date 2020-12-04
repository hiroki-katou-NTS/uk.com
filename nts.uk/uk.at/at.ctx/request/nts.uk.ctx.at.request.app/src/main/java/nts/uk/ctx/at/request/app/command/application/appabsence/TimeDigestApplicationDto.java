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
                new AttendanceTime(overtime60H), 
                new AttendanceTime(nursingTime), 
                new AttendanceTime(childTime),
                new AttendanceTime(timeOff), 
                new AttendanceTime(timeSpecialVacation), 
                new AttendanceTime(timeAnualLeave), 
                Optional.of(specialVacationFrameNO));
    }
}
