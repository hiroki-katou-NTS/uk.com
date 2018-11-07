package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormulaCommand {
    public String formulaCode;
    public YearMonthHistoryItemCommand yearMonthHistoryItem;
}
