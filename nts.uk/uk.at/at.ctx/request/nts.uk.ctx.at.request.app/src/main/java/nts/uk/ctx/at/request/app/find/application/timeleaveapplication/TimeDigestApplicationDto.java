package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;

@Data
public class TimeDigestApplicationDto {

    private int workNo;

    private int appTimeType;

    private int applicationTime;

    private Integer startTime;

    private Integer endTime;

}
