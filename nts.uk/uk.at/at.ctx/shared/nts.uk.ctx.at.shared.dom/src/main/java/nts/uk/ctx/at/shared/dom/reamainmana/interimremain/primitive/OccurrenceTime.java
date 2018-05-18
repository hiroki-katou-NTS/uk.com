package nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * 発生時間数
 * @author do_dt
 *
 */
@TimeRange(max = "48:00", min = "00:00")
public class OccurrenceTime extends TimeDurationPrimitiveValue<OccurrenceTime>{

	public OccurrenceTime(boolean isNegative, int hourPart, int minutePart) {
		super(isNegative, hourPart, minutePart);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
