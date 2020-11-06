package nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@Data
public class TimeDigestApplication {

    private AttendanceTime overtime60H;
    
    private AttendanceTime nursingTime;
    
    private AttendanceTime childTime;
    
    private AttendanceTime timeOff;
    
    private AttendanceTime timeSpecialVacation;
    
    private AttendanceTime timeAnualLeave;
    
    private Optional<Integer> specialVacationFrameNO;
    
    public TimeDigestApplication(AttendanceTime overtime60H, 
            AttendanceTime nursingTime, 
            AttendanceTime childTime, 
            AttendanceTime timeOff, 
            AttendanceTime timeSpecialVacation, 
            AttendanceTime timeAnualLeave, 
            Optional<Integer> specialVacationFrameNO) {
        this.nursingTime = nursingTime;
        this.childTime = childTime;
        this.timeOff = timeOff;
        this.timeSpecialVacation = timeSpecialVacation;
        this.timeAnualLeave = timeAnualLeave;
        this.specialVacationFrameNO = specialVacationFrameNO == null ? Optional.empty() : specialVacationFrameNO;
    }
}
