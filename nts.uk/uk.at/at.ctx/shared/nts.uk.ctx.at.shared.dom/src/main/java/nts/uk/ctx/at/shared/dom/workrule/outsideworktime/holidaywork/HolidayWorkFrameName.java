package nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 休出枠名称
 * @author keisuke_hoshina
 *
 */

@StringMaxLength(12)
public class HolidayWorkFrameName extends CodePrimitiveValue<HolidayWorkFrameName>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs.
	 * @param rawValue raw value
	 */
	public HolidayWorkFrameName(String rawValue) {
		super(rawValue);
	}

}
