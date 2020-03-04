package nts.uk.ctx.pr.core.app.find.wageprovision.formula;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class DetailFormulaConverterDto {
    public Map<String, String> formulaElements;
    public int yearMonth;
}
