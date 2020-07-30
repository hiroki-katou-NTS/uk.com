package nts.uk.ctx.at.schedule.app.find.shift.workcycle;

import lombok.Value;

@Value
public class WorkingCycleInfoDto {

    private String typeCode;

    private String timeCode;

    private int days;

    private int dispOrder;

}
