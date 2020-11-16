package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkplaborcostandtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WkpLaborCostAndTimeDto {

    private int LaborCostAndTimeType;
    private LaborCostAndTimeDto laborCostAndTimeDtos;

    public static List<WkpLaborCostAndTimeDto> setData(Optional<WorkplaceCounterLaborCostAndTime> laborCostAndTime){

        if (laborCostAndTime.isPresent()){
            return laborCostAndTime.get().getLaborCostAndTimeList().entrySet().stream().map(x -> new WkpLaborCostAndTimeDto(
                x.getKey().value,
                new LaborCostAndTimeDto(
                    x.getValue().getUseClassification().value,
                    x.getValue().getTime().value,
                    x.getValue().getLaborCost().value,
                    x.getValue().getBudget().isPresent() ? x.getValue().getBudget().get().value : null)
            )).collect(Collectors.toList());
        }else {
            List<WkpLaborCostAndTimeDto> result = new ArrayList<>();
            for (int x = 0; x <3; x++){
                result.add(new WkpLaborCostAndTimeDto(
                    x,
                    new LaborCostAndTimeDto(0,1,1,1)
                ));
            }
            return result;
        }

    }
}
