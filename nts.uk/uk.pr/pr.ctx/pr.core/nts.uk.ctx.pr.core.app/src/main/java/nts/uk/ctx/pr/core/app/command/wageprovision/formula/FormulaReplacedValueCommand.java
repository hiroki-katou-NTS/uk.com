package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FormulaReplacedValueCommand {
    public String formulaItem;
    public String trialCalculationValue;
}
