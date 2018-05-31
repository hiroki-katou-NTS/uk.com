package nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * 未相殺時間数
 * @author do_dt
 *
 */
@TimeRange(max = "48:00", min = "00:00")
public class UnOffsetTime extends TimeDurationPrimitiveValue<UnOffsetTime>{

	public UnOffsetTime(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
