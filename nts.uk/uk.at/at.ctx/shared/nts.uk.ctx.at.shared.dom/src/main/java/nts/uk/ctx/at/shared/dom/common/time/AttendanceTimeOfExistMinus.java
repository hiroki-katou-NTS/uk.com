package nts.uk.ctx.at.shared.dom.common.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 勤怠時間(マイナス有り)
 * @author keisuke_hoshina
 *
 */
@TimeRange(max = "48:00", min = "-48:00")
public class AttendanceTimeOfExistMinus extends TimeDurationPrimitiveValue<AttendanceTimeOfExistMinus>{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new attendence time.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public AttendanceTimeOfExistMinus(Integer rawValue) {
		super(rawValue);
	}
	
	@Override
	public Integer reviseRawValue(Integer rawValue) {
		if(rawValue > 1880)
			rawValue = 1888;
		if(-1880 > rawValue)
			rawValue = -1880;
		return super.reviseRawValue(rawValue);
	}
}
