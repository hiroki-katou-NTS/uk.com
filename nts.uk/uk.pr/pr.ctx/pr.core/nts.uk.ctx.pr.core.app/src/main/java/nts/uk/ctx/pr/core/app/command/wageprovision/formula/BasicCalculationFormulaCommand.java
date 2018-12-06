package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* かんたん計算式: DTO
*/
@AllArgsConstructor
@Data
@NoArgsConstructor
public class BasicCalculationFormulaCommand {

    private int calculationFormulaClassification;
    private String masterUseCode;
    private String historyID;
    private BigDecimal basicCalculationFormula;
    private Integer standardAmountClassification;
    private BigDecimal standardFixedValue;
    private List<String> targetItemCodeList;
    private String attendanceItem;
    private Integer coefficientClassification;
    private BigDecimal coefficientFixedValue;
    private Integer formulaType;
    private Integer roundingResult;
    private Integer adjustmentClassification;
    private Integer baseItemClassification;
    private BigDecimal baseItemFixedValue;
    private Integer premiumRate;
    private Integer roundingMethod;
    
    
    public BasicCalculationFormula fromCommandToDomain(){
        return this.toBasicCalculationFormula();
    }

    public BasicCalculationFormula toBasicCalculationFormula () {
        return new BasicCalculationFormula(historyID, masterUseCode, calculationFormulaClassification, basicCalculationFormula, this.toBasicCalculationForm());
    }

    public BasicCalculationForm toBasicCalculationForm () {
        return new BasicCalculationForm(this.toBasicCalculationStandardAmount(), this.toBasicCalculationFactorClassification(), formulaType, roundingResult, adjustmentClassification, this.toBasicCalculationItemCategory(), premiumRate, roundingMethod);
    }

    public BasicCalculationStandardAmount toBasicCalculationStandardAmount () {
        return new BasicCalculationStandardAmount(standardAmountClassification, standardFixedValue, targetItemCodeList);
    }

    public BasicCalculationFactorClassification toBasicCalculationFactorClassification () {
        return new BasicCalculationFactorClassification(new CoefficientItem(attendanceItem, coefficientClassification), coefficientFixedValue);
    }

    public Optional<BasicCalculationItemCategory> toBasicCalculationItemCategory () {
        if (baseItemClassification == null) return Optional.empty();
        return Optional.of(new BasicCalculationItemCategory(baseItemClassification, baseItemFixedValue));
    }
    
}
