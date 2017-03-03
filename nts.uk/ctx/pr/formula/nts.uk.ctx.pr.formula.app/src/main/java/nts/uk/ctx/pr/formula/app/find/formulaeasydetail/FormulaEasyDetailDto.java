/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasydetail;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaEasyDetailDto {
	
	String companyCode;
	
	String formulaCode;
	
	String historyId;
	
	String easyFormulaCode;
	
	String easyFormulaName;

	BigDecimal easyFormulaTypeAtr;
	
	BigDecimal baseFixedAmount;

	BigDecimal baseAmountDevision;

	BigDecimal baseFixedValue;

	BigDecimal baseValueDevision;

	BigDecimal premiumRate;

	BigDecimal roundProcessingDevision;

	String coefficientDivision;

	BigDecimal coefficientFixedValue;

	BigDecimal adjustmentDevision;

	BigDecimal totalRounding;
	
	BigDecimal maxLimitValue;
	
	BigDecimal minLimitValue;

	public static FormulaEasyDetailDto fromDomain(FormulaEasyDetail domain) {
		return new FormulaEasyDetailDto(
				domain.getCompanyCode(), 
				domain.getFormulaCode().v(),
				domain.getHistoryId(), 
				domain.getEasyFormulaCode().v(), 
				domain.getEasyFormulaName().v(),
				new BigDecimal(domain.getEasyFormulaTypeAtr().value),
				domain.getBaseFixedAmount().v(),
				new BigDecimal(domain.getBaseAmountDevision().value),
				domain.getBaseFixedValue().v(),
				new BigDecimal(domain.getBaseValueDevision().value),
				domain.getPremiumRate().v(),
				new BigDecimal(domain.getRoundProcessingDevision().value),
				domain.getCoefficientDivision().v(),
				domain.getCoefficientFixedValue().v(),
				new BigDecimal(domain.getAdjustmentDevision().value),
				new BigDecimal(domain.getTotalRounding().value),
				domain.getMaxValue().v(),
				domain.getMinValue().v());
	}

}
