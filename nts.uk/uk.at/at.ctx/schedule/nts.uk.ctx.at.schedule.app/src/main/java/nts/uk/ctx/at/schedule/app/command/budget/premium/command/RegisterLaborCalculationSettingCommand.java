package nts.uk.ctx.at.schedule.app.command.budget.premium.command;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    private PerCostRoundSettingDto personCostRoundingSetting;

    private List<PremiumSettingDto> premiumSettingList;


}
