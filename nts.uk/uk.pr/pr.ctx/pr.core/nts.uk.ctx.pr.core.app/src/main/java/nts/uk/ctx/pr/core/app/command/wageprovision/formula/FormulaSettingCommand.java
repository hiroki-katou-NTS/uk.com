package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaSettingCommand {
    public BasicFormulaSettingCommand basicFormulaSettingCommand;
    public DetailFormulaSettingCommand detailFormulaSettingCommand;
    public List<BasicCalculationFormulaCommand> basicCalculationFormulaCommand;
    private YearMonthHistoryItemCommand yearMonth;
    public void updateIdentifier () {
        this.basicCalculationFormulaCommand = this.basicCalculationFormulaCommand.stream().map(item -> {
            item.historyID = yearMonth.historyID;
            return item;
        }).collect(Collectors.toList());
        this.basicFormulaSettingCommand.historyID = yearMonth.historyID;
        this.detailFormulaSettingCommand.historyID = yearMonth.historyID;
    }
}
