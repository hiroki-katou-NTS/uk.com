package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/** 応援勤務枠NO */
@IntegerRange(min = 1, max = 20)
public class OuenFrameNo extends IntegerPrimitiveValue<OuenFrameNo> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public OuenFrameNo(Integer rawValue) {
		super(rawValue);
	}
}
