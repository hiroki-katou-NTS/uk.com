package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;

/**
 * 1日の時間
 * @author keisuke_hoshina
 *
 */
public class TimeOfDay extends TimeDurationPrimitiveValue<TimeOfDay>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public TimeOfDay(int rawValue) {
		super(rawValue);
	}

}
