package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicCalculationForm;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicCalculationFormula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicCalculationItemCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* かんたん計算式: DTO
*/
@AllArgsConstructor
@Data
@NoArgsConstructor
public class BasicCalculationFormulaDto {

    private int calculationFormulaClassification;
    private String masterUseCode;
    private String historyID;
    private Long basicCalculationFormula;
    private Integer standardAmountClassification;
    private Long standardFixedValue;
    private List<String> targetItemCodeList = new ArrayList<>();
    private String attendanceItem;
    private Integer coefficientClassification;
    private BigDecimal coefficientFixedValue;
    private Integer formulaType;
    private Integer roundingResult;
    private Integer adjustmentClassification;
    private Integer baseItemClassification;
    private Long baseItemFixedValue;
    private Integer premiumRate;
    private Integer roundingMethod;
    
    
    public static BasicCalculationFormulaDto fromDomainToDto(BasicCalculationFormula domain){
        BasicCalculationFormulaDto dto = new BasicCalculationFormulaDto();
        dto.historyID = domain.getHistoryID();
        dto.masterUseCode = domain.getMasterUseCode().v();
        dto.calculationFormulaClassification = domain.getCalculationFormulaClassification().value;
        dto.basicCalculationFormula = domain.getBasicCalculationFormula().map(PrimitiveValueBase::v).orElse(null);
        if (!domain.getBasicCalculationForm().isPresent()) return dto;
        BasicCalculationForm basicCalculationForm = domain.getBasicCalculationForm().get();
        dto.standardAmountClassification = basicCalculationForm.getBasicCalculationStandardAmount().getStandardAmountClassification().value;
        dto.standardFixedValue = basicCalculationForm.getBasicCalculationStandardAmount().getStandardFixedValue().map(PrimitiveValueBase::v).orElse(null);
        dto.targetItemCodeList = basicCalculationForm.getBasicCalculationStandardAmount().getTargetItemCodeList().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
        dto.attendanceItem = basicCalculationForm.getBasicCalculationFactorClassification().getCoefficientItem().getAttendanceItem().map(PrimitiveValueBase::v).orElse(null);
        dto.coefficientClassification = basicCalculationForm.getBasicCalculationFactorClassification().getCoefficientItem().getCoefficientClassification().map(i -> i.value).orElse(null);
        dto.coefficientFixedValue = basicCalculationForm.getBasicCalculationFactorClassification().getCoefficientFixedValue().map(PrimitiveValueBase::v).orElse(null);
        dto.formulaType = basicCalculationForm.getFormulaType().value;
        dto.roundingResult = basicCalculationForm.getRoundingResult().value;
        dto.adjustmentClassification = basicCalculationForm.getAdjustmentClassification().value;
        dto.premiumRate = basicCalculationForm.getPremiumRate().map(PrimitiveValueBase::v).orElse(null);
        dto.roundingMethod = basicCalculationForm.getRoundingMethod().map(i -> i.value).orElse(null);
        if (!basicCalculationForm.getBasicCalculationItemCategory().isPresent()) return dto;
        BasicCalculationItemCategory basicCalculationItemCategory = basicCalculationForm.getBasicCalculationItemCategory().get();
        dto.baseItemClassification = basicCalculationItemCategory.getBaseItemClassification().value;
        dto.baseItemFixedValue = basicCalculationItemCategory.getBaseItemFixedValue().map(PrimitiveValueBase::v).orElse(null);
        return dto;
    }
    
}
