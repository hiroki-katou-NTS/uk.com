package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.HistPersonCostCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PersonCostCalculation;

import java.util.List;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
public class HistAnPerCost {
    HistPersonCostCalculation histPersonCostCalculation;
    List<PersonCostCalculation> personCostCalculation;

}
