package nts.uk.ctx.at.schedule.app.command.budget.premium.command;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PerCostRoundSettingDto {
    // UnitPriceRoundingSetting
    private int unitPriceRounding;

    // AmountRoundingSetting
    private int unit;

    private int rounding;

}
