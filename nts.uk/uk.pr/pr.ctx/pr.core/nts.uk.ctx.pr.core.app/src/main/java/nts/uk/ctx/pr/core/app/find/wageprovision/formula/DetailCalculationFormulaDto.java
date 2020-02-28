package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailCalculationFormula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaElement;

/**
 * 詳細計算式（コード）: DTO
 */
@AllArgsConstructor
@Data
public class DetailCalculationFormulaDto {
    /**
     * 構成順
     */
    private int elementOrder;

    /**
     * 計算式要素
     */
    private String formulaElement;

    public static DetailCalculationFormulaDto fromDomainToDto(DetailCalculationFormula domain)
    {
        return new DetailCalculationFormulaDto(domain.getElementOrder(), domain.getFormulaElement().v());
    }

}
