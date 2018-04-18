package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

@DecimalRange(min = "0", max = "99.5")
@DecimalMantissaMaxLength(1)
public class GrantDays extends DecimalPrimitiveValue<GrantDays> {


	public GrantDays(BigDecimal rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
