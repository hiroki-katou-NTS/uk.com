package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;

@Data
public class TimeLeaveAppDetailCommand {

    private int workNo;

    private int appTimeType;

    private Integer startTime;

    private Integer endTime;

}
