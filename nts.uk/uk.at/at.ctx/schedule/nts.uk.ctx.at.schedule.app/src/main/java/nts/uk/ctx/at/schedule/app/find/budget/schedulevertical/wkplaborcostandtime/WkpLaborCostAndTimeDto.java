package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkplaborcostandtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WkpLaborCostAndTimeDto {

    private int LaborCostAndTimeType;
    private LaborCostAndTimeDto laborCostAndTimeDtos;

    public static List<WkpLaborCostAndTimeDto> setData(WorkplaceCounterLaborCostAndTime laborCostAndTime){

        return laborCostAndTime.getLaborCostAndTimeList().entrySet().stream().map(x -> new WkpLaborCostAndTimeDto(
            x.getKey().value,
            new LaborCostAndTimeDto(
                x.getValue().getUseClassification().value,
                x.getValue().getTime().value,
                x.getValue().getLaborCost().value,
                x.getValue().getBudget().isPresent() ? x.getValue().getBudget().get().value : null)
        )).collect(Collectors.toList());
    }
}
