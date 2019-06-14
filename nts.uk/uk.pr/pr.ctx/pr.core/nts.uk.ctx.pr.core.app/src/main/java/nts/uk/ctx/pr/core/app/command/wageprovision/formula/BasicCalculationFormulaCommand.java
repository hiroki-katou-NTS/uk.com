package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
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

    public String formulaCode;
    public int calculationFormulaClassification;
    public String masterUseCode;
    public String historyID;
    public Long basicCalculationFormula;
    public Integer standardAmountClassification;
    public Long standardFixedValue;
    public List<String> targetItemCodeList;
    public String attendanceItem;
    public Integer coefficientClassification;
    public BigDecimal coefficientFixedValue;
    public Integer formulaType;
    public Integer roundingResult;
    public Integer adjustmentClassification;
    public Integer baseItemClassification;
    public Long baseItemFixedValue;
    public Integer premiumRate;
    public Integer roundingMethod;
    
    
    public BasicCalculationFormula fromCommandToDomain(){
        return this.toBasicCalculationFormula();
    }

    public BasicCalculationFormula toBasicCalculationFormula () {
        BasicCalculationForm basicCalculationForm = null;
        if (calculationFormulaClassification == CalculationFormulaClassification.FORMULA.value && formulaType!= null) basicCalculationForm = this.toBasicCalculationForm();
        return new BasicCalculationFormula(formulaCode, historyID, masterUseCode, calculationFormulaClassification, basicCalculationFormula, basicCalculationForm);
    }

    public BasicCalculationForm toBasicCalculationForm () {
        Optional<BasicCalculationItemCategory> basicCalculationItemCategory = Optional.empty();
        if (formulaType == FormulaType.CALCULATION_FORMULA_TYPE3.value) basicCalculationItemCategory = this.toBasicCalculationItemCategory();
        return new BasicCalculationForm(this.toBasicCalculationStandardAmount(), this.toBasicCalculationFactorClassification(), formulaType, roundingResult, adjustmentClassification, basicCalculationItemCategory, premiumRate, roundingMethod);
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
