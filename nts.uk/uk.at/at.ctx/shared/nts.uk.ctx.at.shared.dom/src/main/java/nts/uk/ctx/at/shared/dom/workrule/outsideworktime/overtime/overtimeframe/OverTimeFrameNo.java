package nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;
/**
 * 残業枠No
 * @author keisuke_hoshina
 *
 */

@DecimalMaxValue("10")
@DecimalMinValue("1")
public class OverTimeFrameNo extends IntegerPrimitiveValue<OverTimeFrameNo>{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public OverTimeFrameNo(Integer rawValue) {
		super(rawValue);
	}
}
