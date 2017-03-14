package nts.uk.ctx.pr.formula.app.find.formula;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.pr.formula.app.command.formulamaster.ReferenceItemCodeDto;

@Data
public class EasyFormulaFindDto {
	
	private String easyFormulaCode;
	
	private BigDecimal value;
	
	/**
	 * B_SEL_003
	 * REF_MASTER_CD
	 */
	private String refMasterNo;
	
}
