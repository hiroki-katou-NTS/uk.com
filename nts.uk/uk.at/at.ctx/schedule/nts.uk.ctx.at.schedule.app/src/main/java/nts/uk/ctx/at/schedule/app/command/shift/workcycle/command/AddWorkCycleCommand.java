package nts.uk.ctx.at.schedule.app.command.shift.workcycle.command;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class AddWorkCycleCommand {

    private String workCycleCode;

    private String workCycleName;

    private List<WorkInformation> workInformations;

    public static WorkCycle createFromCommand(AddWorkCycleCommand command, String cid) {
        List<WorkCycleInfo> infos = command.workInformations.stream().map(i -> new WorkCycleInfo(
                i.getDays(),
                i.getWorkTypeCode(),
                i.getWorkTimeCode(),
                i.getDispOrder()
        )).collect(Collectors.toList());
        return new WorkCycle(
                cid,
                command.getWorkCycleCode(),
                command.getWorkCycleName(),
                infos
        );
    }

}
