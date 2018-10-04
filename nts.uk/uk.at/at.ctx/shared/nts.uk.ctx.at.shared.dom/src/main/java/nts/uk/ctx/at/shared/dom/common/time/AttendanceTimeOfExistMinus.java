package nts.uk.ctx.at.shared.dom.common.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.uk.shr.com.enumcommon.DayAttr;

/**
 * 勤怠時間(マイナス有り)
 * @author keisuke_hoshina
 *
 */
@TimeRange(max = "48:00", min = "-48:00")
public class AttendanceTimeOfExistMinus extends TimeDurationPrimitiveValue<AttendanceTimeOfExistMinus>{
	
	public static AttendanceTimeOfExistMinus ZERO = new AttendanceTimeOfExistMinus(0);
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** minutes of day */
	public static final int MINUTES_OF_DAY = 24 * 60;

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
		if(rawValue > 2880)
			rawValue = 2880;
		if(-2880 > rawValue)
			rawValue = -2880;
		return super.reviseRawValue(rawValue);
	}
	
	/**
	 * Returns days offset
	 * example:
	 * -1440 ...    -1 -> -1
	 *     0 ...  1439 ->  0 
	 *  1440 ...  2880 ->  1
	 * 
	 * @return days offset
	 */
	public int daysOffset() {
		return this.isNegative()
				? (this.valueAsMinutes() + 1) / MINUTES_OF_DAY - 1
				: this.valueAsMinutes() / MINUTES_OF_DAY;
	}
	
	public DayAttr dayAttr(){
		
		switch (this.daysOffset()) {
		case 0:
			return DayAttr.THE_PRESENT_DAY;
		case 1:
			return DayAttr.THE_NEXT_DAY;
		case 2:
			return DayAttr.TWO_DAY_LATER;
		case -1:
			return DayAttr.THE_PREVIOUS_DAY;
		case -2:
			return DayAttr.TWO_DAY_EARLIER;
		default:
			throw new RuntimeException("not supported day attr: " + this.v());
		}
	}
	
	public String getInDayTimeWithFormat(){
		return this.hour() + ":" + (this.minute() < 10 ? "0" + this.minute() : this.minute());
	}
	
	public String getFullText() {
		// とりあえず日本語のみに対応
		// 英語等の場合にどうなるか、まだ仕様が決まっていない
		return this.dayAttr().description + this.getInDayTimeWithFormat();
	}
	
	@Override
	public int hour() {
		return super.hour() % 24;
	}
	
	public int rawHour(){
		return this.v() / 60;
	}
}
