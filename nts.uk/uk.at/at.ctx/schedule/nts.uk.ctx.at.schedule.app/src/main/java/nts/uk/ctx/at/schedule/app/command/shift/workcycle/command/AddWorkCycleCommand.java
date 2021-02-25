package nts.uk.ctx.at.schedule.app.command.shift.workcycle.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;

@Value
public class AddWorkCycleCommand {

    // 勤務サイクルコード
    private String workCycleCode;

    // 勤務サイクル名称
    private String workCycleName;

    // 勤務サイクルの勤務情報
    private List<WorkInformation> workInformations;

    public static WorkCycle createFromCommand(AddWorkCycleCommand command, String cid) {
        // Sort list workinformation by display order
        command.workInformations.sort(Comparator.comparingDouble(WorkInformation::getDispOrder));

        List<WorkCycleInfo> infos =command.workInformations.stream().map(i -> WorkCycleInfo.create(
                i.getDays(),
                new nts.uk.ctx.at.shared.dom.WorkInformation(i.getWorkTypeCode(), i.getWorkTimeCode())
        )).collect(Collectors.toList());
        return WorkCycle.create(
                cid,
                command.getWorkCycleCode(),
                command.getWorkCycleName(),
                infos
        );
    }

    public static WorkCycle createTemp(String cid) {
        WorkInformation info = new WorkInformation("code", "timeCd", 1, 2);
        List<WorkInformation> infos = new ArrayList<>();
        infos.add(info);
        AddWorkCycleCommand workCycle = new AddWorkCycleCommand(
                "code", "name", infos
        );
        return AddWorkCycleCommand.createFromCommand(workCycle, cid);
    }

}
