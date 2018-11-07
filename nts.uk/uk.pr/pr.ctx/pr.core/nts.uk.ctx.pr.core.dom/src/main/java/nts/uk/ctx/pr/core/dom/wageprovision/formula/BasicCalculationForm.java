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
* かんたん計算式本体
*/
@AllArgsConstructor
@Getter
public class BasicCalculationForm extends DomainObject {

    /**
     * かんたん計算基準金額項目
     */
    private BasicCalculationStandardAmount basicCalculationStandardAmount;

    /**
     * かんたん計算係数区分
     */
    private BasicCalculationFactorClassification basicCalculationFactorClassification;

    /**
     * 計算式タイプ
     */
    private FormulaType formulaType;

    
    /**
    * 結果端数処理
    */
    private RoundingResult roundingResult;
    
    /**
    * 調整区分
    */
    private AdjustmentClassification adjustmentClassification;

    /**
     * かんたん計算基底項目区分
     */
    private Optional<BasicCalculationItemCategory> basicCalculationItemCategory;

    /**
     * 割増率
     */
    private Optional<PremiumRate> premiumRate;

    /**
     * 式中端数処理
     */
    private Optional<RoundingMethod> roundingMethod;

    
    public BasicCalculationForm(BasicCalculationStandardAmount basicCalculationStandardAmount, BasicCalculationFactorClassification basicCalculationFactorClassification, int formulaType, int roundingResult, int adjustmentClassification,  BasicCalculationItemCategory basicCalculationItemCategory, Integer premiumRate, Integer roundingMethod) {
        this.premiumRate = premiumRate == null ? Optional.empty() : Optional.of(new PremiumRate(premiumRate));
        this.roundingMethod = roundingMethod == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(roundingMethod, RoundingMethod.class));
        this.roundingResult = EnumAdaptor.valueOf(roundingResult, RoundingResult.class);
        this.adjustmentClassification = EnumAdaptor.valueOf(adjustmentClassification, AdjustmentClassification.class);
        this.formulaType = EnumAdaptor.valueOf(formulaType, FormulaType.class);
        this.basicCalculationStandardAmount = basicCalculationStandardAmount;
        this.basicCalculationItemCategory = Optional.ofNullable(basicCalculationItemCategory);
        this.basicCalculationFactorClassification = basicCalculationFactorClassification;
    }
    
}
