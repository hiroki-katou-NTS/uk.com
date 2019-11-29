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
     * 会社ID
     */
    private String companyId;

    /**
     * 計算式コード
     */
    private FormulaCode formulaCode;

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
    
    public BasicCalculationFormula(String formulaCode, String historyID, String masterUseCode, int calculationFormulaClassification, Long basicCalculationFormula, BasicCalculationForm basicCalculationForm) {
        this.formulaCode = new FormulaCode(formulaCode);
        this.historyID = historyID;
        this.masterUseCode = new MasterUseCode(masterUseCode);
        this.calculationFormulaClassification = EnumAdaptor.valueOf(calculationFormulaClassification, CalculationFormulaClassification.class);
        this.basicCalculationFormula = Optional.empty();
        this.basicCalculationForm = Optional.empty();
        if (this.calculationFormulaClassification == CalculationFormulaClassification.FORMULA) this.basicCalculationForm = basicCalculationForm == null ? Optional.empty() : Optional.of(basicCalculationForm);
        else if (this.calculationFormulaClassification == CalculationFormulaClassification.FIXED_VALUE) this.basicCalculationFormula = basicCalculationFormula == null ? Optional.empty() : Optional.of(new FixedAmount(basicCalculationFormula));
    }
}
