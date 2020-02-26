package nts.uk.ctx.pr.core.app.command.wageprovision.formula;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class DetailFormulaCommand {
    public String formulaContent;
    public List<FormulaReplacedValueCommand> replaceValues;
    public int roundingMethod;
    public int roundingPosition;
}
