package nts.uk.ctx.exio.dom.input.validation.user.type.numeric.real;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 受入条件実数
 */
@DecimalMaxValue("9999999999.99")
@DecimalMinValue("-9999999999.99")
@DecimalMantissaMaxLength(2)
public class ImportingConditionReal extends DecimalPrimitiveValue<ImportingConditionReal>{

	private static final long serialVersionUID = 1L;
	
	public ImportingConditionReal(BigDecimal rawValue) {
		super(rawValue);
	}
}
