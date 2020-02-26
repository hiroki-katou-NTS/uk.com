package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailCalculationFormula;

/**
 * 詳細計算式（コード）: DTO
 */
@AllArgsConstructor
@Data
public class DetailCalculationFormulaCommand {
    /**
     * 構成順
     */
    private int elementOrder;

    /**
     * 計算式要素
     */
    private String formulaElement;

    public DetailCalculationFormula fromCommandToDomain (){
        return new DetailCalculationFormula(elementOrder, formulaElement);
    }

}
