package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 詳細計算式（コード）
*/
@AllArgsConstructor
@Getter
public class DetailCalculationFormula extends DomainObject
{
    
    /**
    * 構成順
    */
    private int elementOrder;
    
    /**
    * 計算式要素
    */
    private FormulaElement formulaElement;
    
    public DetailCalculationFormula(int elementOrder, String formulaElement) {
        this.elementOrder = elementOrder;
        this.formulaElement = new FormulaElement(formulaElement);
    }
    
}
