package nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 
 * @author nampt
 * 外出枠NO
 *
 */
@DecimalMinValue("1")
@DecimalMaxValue("10")
public class OutingFrameNo extends DecimalPrimitiveValue<OutingFrameNo> {
	
	private static final long serialVersionUID = 1L;
	
	public OutingFrameNo(BigDecimal rawValue) {
		super(rawValue);
	}

}
