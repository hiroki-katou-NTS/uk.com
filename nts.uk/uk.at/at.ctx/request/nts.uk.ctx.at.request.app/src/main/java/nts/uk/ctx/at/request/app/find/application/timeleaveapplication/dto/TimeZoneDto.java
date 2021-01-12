package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

@AllArgsConstructor
@Data
public class TimeZoneDto {
    private int workNo;
    private Integer startTime;
    private Integer endTime;

    public TimeZone toTimeZone() {
        return new TimeZone(new TimeWithDayAttr(startTime), new TimeWithDayAttr(endTime));
    }
}
