package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* かんたん計算式
*/
@Getter
public class BasicCalculationFormula extends AggregateRoot {
    

    /**
    * 固定/計算式/既定区分
    */
    private CalculationFormulaClassification calculationFormulaClassification;

    /**
     * 使用マスタコード
     */
    private MasterUseCode masterUseCode;

    /**
     * 履歴ID
     */
    private String historyID;
    /**
    * かんたん計算式固定金額
    */

    private Optional<FixedAmount> basicCalculationFormula;

    /**
    * かんたん計算式本体
    */
    private Optional<BasicCalculationForm> basicCalculationForm;
    
    public BasicCalculationFormula(String masterUseCode, int calculationFormulaClassification, String historyID, BigDecimal basicCalculationFormula, BasicCalculationForm basicCalculationForm) {
        this.masterUseCode = new MasterUseCode(masterUseCode);
        this.calculationFormulaClassification = EnumAdaptor.valueOf(calculationFormulaClassification, CalculationFormulaClassification.class);
        this.basicCalculationFormula = basicCalculationFormula == null ? Optional.empty() : Optional.of(new FixedAmount(basicCalculationFormula));
        this.basicCalculationForm = Optional.ofNullable(basicCalculationForm);
        this.historyID = historyID;
    }
    
}
