package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaSettingCommand {
    public BasicFormulaSettingCommand basicFormulaSettingCommand;
    public DetailFormulaSettingCommand detailFormulaSettingCommand;
    public List<BasicCalculationFormulaCommand> basicCalculationFormulaCommand;
    private YearMonthHistoryItemCommand yearMonth;
}
