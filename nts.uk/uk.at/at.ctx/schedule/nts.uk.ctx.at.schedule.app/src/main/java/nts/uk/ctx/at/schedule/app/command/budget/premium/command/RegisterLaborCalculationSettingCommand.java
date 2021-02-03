package nts.uk.ctx.at.schedule.app.command.budget.premium.command;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterLaborCalculationSettingCommand {
    private GeneralDate startDate;

    private Integer unitPrice;

    private int howToSetUnitPrice;

    private Integer workingHoursUnitPrice;

    private  String memo;

    private PerCostRoundSettingDto personCostRoundingSetting;

    private List<PremiumSettingDto> premiumSets;

}
