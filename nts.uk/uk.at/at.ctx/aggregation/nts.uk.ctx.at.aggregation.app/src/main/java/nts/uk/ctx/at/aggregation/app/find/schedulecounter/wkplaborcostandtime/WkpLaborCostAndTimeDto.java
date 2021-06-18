package nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkplaborcostandtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime.WorkplaceCounterLaborCostAndTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WkpLaborCostAndTimeDto {

    private int LaborCostAndTimeType;
    private LaborCostAndTimeDto laborCostAndTimeDtos;

    public static List<WkpLaborCostAndTimeDto> setData(WorkplaceCounterLaborCostAndTime laborCostAndTime) {

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
