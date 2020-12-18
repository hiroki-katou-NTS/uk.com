package nts.uk.ctx.at.schedule.app.command.budget.premium.command;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostRoundingSetting;

import javax.inject.Inject;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterLaborCalculationSettingCommand {
    private String historyId;

    private Integer unitPrice;

    private int howToSetUnitPrice;

    private Integer workingHoursUnitPrice;

    private  String remarks;

    private PersonCostRoundingSetting personCostRoundingSetting;


}
