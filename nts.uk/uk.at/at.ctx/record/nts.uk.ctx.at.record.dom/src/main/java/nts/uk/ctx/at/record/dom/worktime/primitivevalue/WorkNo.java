package nts.uk.ctx.at.record.dom.worktime.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 
 * @author nampt
 * 勤務NO
 *
 */
@DecimalMinValue("1")
@DecimalMaxValue("3")
public class WorkNo extends DecimalPrimitiveValue<WorkNo>{

	private static final long serialVersionUID = 1L;
	
	public WorkNo(BigDecimal rawValue) {
		super(rawValue);
	}
}
