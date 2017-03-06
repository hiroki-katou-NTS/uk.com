package nts.uk.ctx.pr.formula.app.command.formulaeasydetail;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddFormulaEasyDetailCommand {

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

}
