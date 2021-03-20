package nts.uk.ctx.at.schedule.app.find.budget.premium.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.PersonCostCalculationDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LisHistAndPersonCostLastDto {
    private List<PersonCostCalDto> personCostCalLast;
}
