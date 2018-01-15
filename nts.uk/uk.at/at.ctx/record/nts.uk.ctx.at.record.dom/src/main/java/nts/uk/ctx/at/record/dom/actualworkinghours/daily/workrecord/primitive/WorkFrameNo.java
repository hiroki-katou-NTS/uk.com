package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.primitive;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

public class WorkFrameNo extends IntegerPrimitiveValue<OverTimeFrameNo>{

	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue the raw value
	 */
	public WorkFrameNo(Integer rawValue) {
		super(rawValue);
	}
}
