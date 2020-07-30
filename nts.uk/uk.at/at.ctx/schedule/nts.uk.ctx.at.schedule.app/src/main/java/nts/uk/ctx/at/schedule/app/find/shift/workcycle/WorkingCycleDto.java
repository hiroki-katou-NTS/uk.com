package nts.uk.ctx.at.schedule.app.find.shift.workcycle;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;

@Value
public class WorkingCycleDto {

    private String name;

    private String code;

    public static WorkingCycleDto fromDomain(WorkCycle domain) {
        return new WorkingCycleDto(domain.getCode().v(), domain.getName().v());
    }

}
