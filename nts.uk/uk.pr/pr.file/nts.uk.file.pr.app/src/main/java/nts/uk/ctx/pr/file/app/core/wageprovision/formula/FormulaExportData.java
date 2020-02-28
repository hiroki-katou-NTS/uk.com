package nts.uk.ctx.pr.file.app.core.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.MasterUseDto;

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
    private List<MasterUseDto> employments;
    private List<MasterUseDto> departments;
    private List<MasterUseDto> cls;
    private List<MasterUseDto> jobs;

}
