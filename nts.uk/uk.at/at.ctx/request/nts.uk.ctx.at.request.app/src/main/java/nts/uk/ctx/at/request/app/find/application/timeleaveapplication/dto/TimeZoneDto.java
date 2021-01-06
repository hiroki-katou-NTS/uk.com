package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TimeZoneDto {
    private int workNo;
    private Integer startTime;
    private Integer endTime;
}
