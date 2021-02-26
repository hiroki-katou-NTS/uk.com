package nts.uk.ctx.at.schedule.app.find.budget.premium.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.PerCostRoundSettingDto;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonCostCalDto {
    private GeneralDate startDate;
    private GeneralDate endDate;
    private String historyId;
    private String companyId;
    private Integer unitPrice;

    private int howToSetUnitPrice;

    private Integer workingHoursUnitPrice;

    private String remarks;

    private PerCostRoundSettingDto personCostRoundingSetting;

    private List<PremiumSettingAndNameDto> premiumSettingList;
}
