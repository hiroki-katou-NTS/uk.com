package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;

import javax.xml.soap.Detail;

/**
* 詳細計算式
*/
@Getter
public class DetailFormulaSetting extends AggregateRoot {

    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 計算式コード
     */
    private FormulaCode formulaCode;
    /**
     * 履歴ID
     */

    private String historyId;

    /**
     * 参照月
     */
    private ReferenceMonth referenceMonth;

    /**
     * 詳細計算式（コード）
     */
    private List<DetailCalculationFormula> detailCalculationFormula;

    /**
    * 端数処理(詳細計算式)
    */
    private Rounding roundingMethod;
    
    /**
    * 端数処理位置
    */
    private RoundingPosition roundingPosition;
    

    

    
    public DetailFormulaSetting(String formulaCode, String historyId, int referenceMonth, List<DetailCalculationFormula> detailCalculationFormula, int roundingMethod, int roundingPosition) {
        this.formulaCode = new FormulaCode(formulaCode);
        this.roundingMethod = EnumAdaptor.valueOf(roundingMethod, Rounding.class);
        this.roundingPosition = EnumAdaptor.valueOf(roundingPosition, RoundingPosition.class);
        this.referenceMonth = EnumAdaptor.valueOf(referenceMonth, ReferenceMonth.class);
        this.detailCalculationFormula = detailCalculationFormula;
        this.historyId = historyId;
    }
    
}
