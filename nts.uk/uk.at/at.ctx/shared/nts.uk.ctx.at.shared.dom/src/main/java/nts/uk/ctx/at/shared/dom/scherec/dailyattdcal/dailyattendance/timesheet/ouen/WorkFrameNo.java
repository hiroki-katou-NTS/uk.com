package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/** 応援勤務枠NO */
@IntegerRange(min = 1, max = 20)
public class WorkFrameNo extends IntegerPrimitiveValue<WorkFrameNo> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public WorkFrameNo(Integer rawValue) {
		super(rawValue);
	}
	
	public static WorkFrameNo of(Integer value) {
		return new WorkFrameNo(value);
	}
}
