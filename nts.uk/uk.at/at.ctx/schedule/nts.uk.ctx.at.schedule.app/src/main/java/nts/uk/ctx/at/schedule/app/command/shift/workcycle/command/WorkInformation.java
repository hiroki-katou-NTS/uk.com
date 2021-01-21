package nts.uk.ctx.at.schedule.app.command.shift.workcycle.command;

import lombok.Value;

@Value
public class WorkInformation {

    private String workTypeCode;

    private String workTimeCode;

    private int dispOrder;

    private int days;
}
