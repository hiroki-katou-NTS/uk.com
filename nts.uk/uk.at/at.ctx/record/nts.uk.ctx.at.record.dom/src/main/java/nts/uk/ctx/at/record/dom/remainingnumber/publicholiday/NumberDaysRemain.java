package nts.uk.ctx.at.record.dom.remainingnumber.publicholiday;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

@DecimalMinValue("-999.5")
@DecimalMaxValue("999.5")
public class NumberDaysRemain extends DecimalPrimitiveValue<NumberDaysRemain>{

	private static final long serialVersionUID = 1L;

	public NumberDaysRemain(BigDecimal rawValue) {
		super(rawValue);
	}

}
