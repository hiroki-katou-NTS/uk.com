package nts.uk.ctx.pr.formula.app.command.formulamaster;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @author nampt
 *
 */
@Data
public class UpdateFormulaMasterCommand {

	private String ccd;

	private String formulaCode;

	private String formulaName;

	/**
	 * B_SEL_001
	 */
	private BigDecimal difficultyAtr;

	/* Formula History */
	private String historyId;

	private int startDate;

	private int endDate;

	/* Formula Condition */
	/**
	 * B_SEL_002
	 * FIX_FORMULA_ATR
	 */
	private int conditionAtr;

	/**
	 * B_SEL_003
	 * REF_MASTER_CD
	 */
	private int refMasterNo;
	
	private int fixFormulaAtr;

	private List<EasyFormulaDto> easyFormulaCode;

	/* Formula Detail */	
	private String easyFormulaName;

	private int easyFormulaTypeAtr;

	private BigDecimal baseFixedAmount;

	private int baseAmountDevision;

	private BigDecimal baseFixedValue;

	private int baseValueDevision;

	private BigDecimal premiumRate;

	private int roundProcessingDevision;

	private String coefficientDivision;

	private BigDecimal coefficientFixedValue;

	private int adjustmentDevision;

	private int totalRounding;

	private BigDecimal maxValue;
	
	private BigDecimal minValue;
	
	/* Formula Manual */
	private String formulaContent;

	private int referenceMonthAtr;

	private int roundAtr;

	private int roundDigit;
}
