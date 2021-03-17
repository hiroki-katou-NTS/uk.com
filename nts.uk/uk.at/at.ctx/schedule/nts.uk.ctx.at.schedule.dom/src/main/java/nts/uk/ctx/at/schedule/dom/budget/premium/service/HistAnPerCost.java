package nts.uk.ctx.at.schedule.dom.budget.premium.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.budget.premium.HistPersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;

import java.util.List;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
public class HistAnPerCost {
    HistPersonCostCalculation histPersonCostCalculation;
    List<PersonCostCalculation> personCostCalculation;

}
