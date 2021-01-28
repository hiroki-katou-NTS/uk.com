package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TimeZoneCommand {
    private int workNo;
    private Integer startTime;
    private Integer endTime;
}
