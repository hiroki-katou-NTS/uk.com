package nts.uk.ctx.pr.formula.app.command.formulamaster;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class EasyFormulaDto {
	
	private String easyFormulaCode;
	
	private BigDecimal value;

	private List<ReferenceItemCodeDto> referenceItemCode;

	/**
	 * @param easyFormulaCode
	 * @param value
	 * @param referenceItemCode
	 */
	public EasyFormulaDto(String easyFormulaCode, BigDecimal value, List<ReferenceItemCodeDto> referenceItemCode) {
		super();
		this.easyFormulaCode = easyFormulaCode;
		this.value = value;
		this.referenceItemCode = referenceItemCode;
	}

	
}
