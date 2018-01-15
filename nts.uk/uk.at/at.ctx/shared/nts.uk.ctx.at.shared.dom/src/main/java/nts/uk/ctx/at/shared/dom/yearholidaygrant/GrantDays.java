package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

@DecimalRange(min = "0", max = "999")
public class GrantDays extends DecimalPrimitiveValue<GrantDays> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrantDays(BigDecimal rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
