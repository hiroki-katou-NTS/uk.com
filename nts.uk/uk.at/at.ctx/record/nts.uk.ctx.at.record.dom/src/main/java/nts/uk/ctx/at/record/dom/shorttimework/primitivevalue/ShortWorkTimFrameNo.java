package nts.uk.ctx.at.record.dom.shorttimework.primitivevalue;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;

/**
 * 
 * @author nampt
 * 短時間勤務枠NO
 *
 */
@DecimalMinValue("1")
@DecimalMaxValue("2")
public class ShortWorkTimFrameNo extends DecimalPrimitiveValue<ShortWorkTimFrameNo> {

	private static final long serialVersionUID = 1L;
	
	public ShortWorkTimFrameNo(BigDecimal rawValue) {
		super(rawValue);
	}
}
