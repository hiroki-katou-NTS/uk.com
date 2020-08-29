package nts.uk.ctx.at.schedule.app.command.shift.workcycle.command;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class AddWorkCycleCommand {

    private String workCycleCode;

    private String workCycleName;

    private List<WorkInformation> workInformations;

    public static WorkCycle createFromCommand(AddWorkCycleCommand command, String cid) {
        List<WorkCycleInfo> infos = command.workInformations.stream().map(i -> WorkCycleInfo.WorkCycleInfo(
                i.getDays(),
                new nts.uk.ctx.at.shared.dom.WorkInformation(i.getWorkTimeCode(), i.getWorkTypeCode())
        )).collect(Collectors.toList());
        return WorkCycle.WorkCycle(
                cid,
                command.getWorkCycleCode(),
                command.getWorkCycleName(),
                infos
        );
    }

    public static WorkCycle createTemp(String cid) {
        WorkInformation info = new WorkInformation("code","timeCd", 1, 2);
        List<WorkInformation> infos = new ArrayList<>();
        infos.add(info);
        AddWorkCycleCommand workCycle = new AddWorkCycleCommand(
                "code", "name", infos
        );
        return AddWorkCycleCommand.createFromCommand(workCycle, cid);
    }

}
