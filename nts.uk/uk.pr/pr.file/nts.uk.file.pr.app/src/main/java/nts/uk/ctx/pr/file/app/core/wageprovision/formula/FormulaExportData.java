package nts.uk.ctx.pr.file.app.core.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormulaExportData {
    private List<Object[]> formulas;
    private List<Object[]> formulaDetails;
    private List<Object[]> targetItems;
    private String companyName;
    private int startDate;
}
