package nts.uk.ctx.pr.formula.app.find.formulaeasycondition;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EasyFormulaConditionDto {

	String easyFormulaCode;

	BigDecimal fixMoney;

	String referenceMasterCode;
}
