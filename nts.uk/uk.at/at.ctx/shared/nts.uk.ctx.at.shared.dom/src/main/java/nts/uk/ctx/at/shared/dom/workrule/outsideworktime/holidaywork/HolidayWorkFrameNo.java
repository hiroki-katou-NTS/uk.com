package nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 休出枠No
 * @author ken_takasu
 *
 */
@DecimalMaxValue("10")
@DecimalMinValue("1")
public class HolidayWorkFrameNo extends IntegerPrimitiveValue<HolidayWorkFrameNo>{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor
	 * @param rawValue
	 */
	public HolidayWorkFrameNo(Integer rawValue) {
		super(rawValue);
	}
}
