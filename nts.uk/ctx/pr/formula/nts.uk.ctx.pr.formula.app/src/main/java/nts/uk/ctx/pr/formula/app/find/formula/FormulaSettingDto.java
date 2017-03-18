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
	
	List<FormulaEasyFinderDto> formulaEasyDetail;
	
	/**
	 * formula manual
	 */
	private String formulaContent;

	private BigDecimal referenceMonthAtr;

	private BigDecimal roundAtr;

	private BigDecimal roundDigit;

}
