package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicFormulaSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.MasterUseDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaSettingDto {
    public BasicFormulaSettingDto basicFormulaSettingDto;
    public DetailFormulaSettingDto detailFormulaSettingDto;
    public List<BasicCalculationFormulaDto> basicCalculationFormulaDto;
    public List<MasterUseDto> masterUseDto;
}
