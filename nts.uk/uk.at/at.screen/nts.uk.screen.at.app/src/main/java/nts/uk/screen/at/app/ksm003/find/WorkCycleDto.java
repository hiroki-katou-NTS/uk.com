package nts.uk.screen.at.app.ksm003.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCycleDto {

    private String code;

    private String name;

    private List<WorkCyleInfoDto> infos;

    public static WorkCycleDto createFromDomain(WorkCycle domain, Map<String , String> workTypes, Map<String , String> workTimeItems) {
        AtomicInteger index = new AtomicInteger();
        List<WorkCyleInfoDto> infos = domain.getInfos().stream().map(i -> new WorkCyleInfoDto(
                i.getWorkInformation().getWorkTypeCode().v(),
                i.getWorkInformation().getWorkTimeCode() != null? i.getWorkInformation().getWorkTimeCode().v(): null,
                i.getDays().v(),
                index.incrementAndGet(),
                workTypes,
                workTimeItems
        )).collect(Collectors.toList());
        return new WorkCycleDto(domain.getCode().v(), domain.getName().v(), infos);
    }

    public static WorkCycleDto startScreen(WorkCycle domain) {
        return new WorkCycleDto(domain.getCode().v(), domain.getName().v(), Collections.emptyList());
    }

}
