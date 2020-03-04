package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormulaSearchDto {
    public String historyID;
    public Boolean withSetting;
    public Integer masterUse;
}
