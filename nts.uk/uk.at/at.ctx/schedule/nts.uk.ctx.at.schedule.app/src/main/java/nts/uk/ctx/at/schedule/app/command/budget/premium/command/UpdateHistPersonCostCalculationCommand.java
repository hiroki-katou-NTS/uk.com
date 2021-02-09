package nts.uk.ctx.at.schedule.app.command.budget.premium.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

import java.util.List;


@Getter
@AllArgsConstructor
public class UpdateHistPersonCostCalculationCommand {
    public GeneralDate startDate;
    public String historyID;

    private Integer unitPrice;

    private int howToSetUnitPrice;

    private Integer workingHoursUnitPrice;

    private  String memo;

    private PerCostRoundSettingDto personCostRoundingSetting;

    private List<PremiumSettingDto> premiumSets;
}
