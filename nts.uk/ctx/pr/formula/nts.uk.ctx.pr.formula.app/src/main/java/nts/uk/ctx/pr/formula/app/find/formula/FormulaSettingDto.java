package nts.uk.ctx.pr.formula.app.find.formula;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class FormulaSettingDto {
	
	/**
	 * C_SEL_002
	 * FIX_FORMULA_ATR
	 */
	private int fixFormulaAtr;
	
	private List<EasyFormulaFindDto> easyFormula;
	
	/**
	 * formula detail
	 */
	private String easyFormulaName;

	private BigDecimal easyFormulaTypeAtr;
	
	private BigDecimal baseFixedAmount;

	private BigDecimal baseAmountDevision;

	private BigDecimal baseFixedValue;

	private BigDecimal baseValueDevision;

	private BigDecimal premiumRate;

	private BigDecimal roundProcessingDevision;

	private String coefficientDivision;

	private BigDecimal coefficientFixedValue;

	private BigDecimal adjustmentDevision;

	private BigDecimal totalRounding;
	
	private BigDecimal maxLimitValue;
	
	private BigDecimal minLimitValue;
	
	/**
	 * formula manual
	 */
	private String formulaContent;

	private BigDecimal referenceMonthAtr;

	private BigDecimal roundAtr;

	private BigDecimal roundDigit;

}
