package nts.uk.ctx.at.record.dom.standardtime.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 
 * @author nampt
 *
 */
@DecimalRange(min = "0", max = "527040")
public class AlarmOneYear extends DecimalPrimitiveValue<AlarmOneYear>{
	
	public AlarmOneYear(BigDecimal rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
	
}
