package nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * 使用時間数
 * @author do_dt
 *
 */
@TimeRange(max = "48:00", min = "00:00")
public class UseTime extends TimeDurationPrimitiveValue<UseTime>{

	public UseTime(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
