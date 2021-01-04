package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeLeaveAppDetailDto {

    private Integer workNo;

    private int appTimeType;

    private Integer startTime;

    private Integer endTime;

}
