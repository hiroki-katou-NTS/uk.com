package nts.uk.ctx.at.schedule.app.find.shift.workcycle;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class WorkingCycleDtlDto {

    private String workCycleCode;
    private String workCycleName;
    private List<WorkingCycleInfoDto> infos;


    public static WorkingCycleDtlDto fromDomain(WorkCycle domain) {
        List<WorkingCycleInfoDto> infoFromDomain = domain.getInfos().stream().map(i -> new WorkingCycleInfoDto(
                i.getWorkInformation().getWorkTypeCode().v(),
                i.getWorkInformation().getWorkTimeCode().v(),
                i.getDays().v(),
                i.getDispOrder().v()
        )).collect(Collectors.toList());
        return new WorkingCycleDtlDto(
                domain.getCode().v(),
                domain.getName().v(),
                infoFromDomain
        );
    }
}
