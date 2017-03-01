package nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * @author tuongvc
 *
 */
@DecimalMinValue(value = "0")
@DecimalMaxValue(value = "9999999999")
public class CommuNoTaxLimitValue extends DecimalPrimitiveValue<CommuNoTaxLimitValue> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor CommuNoTaxLimitValue class.
	 * 
	 * @param rawValue
	 */
	public CommuNoTaxLimitValue(BigDecimal rawValue) {
		super(rawValue);
	}

}
