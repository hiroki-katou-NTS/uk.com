package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 	時間年休上限時間
 * @author masaaki_jinno
 *
 */
@TimeRange(min ="0:00", max="999:59")
public class MaxMinutes extends TimeDurationPrimitiveValue<MaxMinutes>{

	private static final long serialVersionUID = -8135525580841453174L;

	public MaxMinutes(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
