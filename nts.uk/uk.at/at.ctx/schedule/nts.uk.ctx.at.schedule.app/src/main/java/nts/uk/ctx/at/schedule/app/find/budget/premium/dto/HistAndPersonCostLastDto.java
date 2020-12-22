package nts.uk.ctx.at.schedule.app.find.budget.premium.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.PersonCostCalculationDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class HistAndPersonCostLastDto {

    private List<HistPersonCostCalculationDto> lisHist;
    private PersonCostCalculationDto personCostCalLast;
}
