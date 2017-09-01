package nts.uk.shr.com.primitive;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.gul.util.Time;
import nts.uk.shr.com.enumcommon.DayAttr;


@IntegerRange(max = 4319, min = -720)
public class TimeWithDayAttr extends IntegerPrimitiveValue<TimeWithDayAttr>{
	
	private static final int MAX_MINUTES_IN_DAY = Time.MAX_HOUR * Time.MAX_MS;

	/** 12:00 at the previous day */
	public static final TimeWithDayAttr THE_PREVIOUS_DAY_1200 = new TimeWithDayAttr(-12 * Time.MAX_MS);
	
	/** 00:00 at the present day */
	public static final TimeWithDayAttr THE_PRESENT_DAY_0000 = new TimeWithDayAttr(0);
	
	/** 00:00 at the next day */
	public static final TimeWithDayAttr THE_NEXT_DAY_0000 = new TimeWithDayAttr(1 * MAX_MINUTES_IN_DAY);
	
	/** 00:00 at two days later */
	public static final TimeWithDayAttr TWO_DAYS_LATER_0000 = new TimeWithDayAttr(2 * MAX_MINUTES_IN_DAY);
	
	/** 23:59 at two days later */
	public static final TimeWithDayAttr TWO_DAYS_LATER_2359 = new TimeWithDayAttr(3 * MAX_MINUTES_IN_DAY - 1);
	
	private static final long serialVersionUID = 1L;

	public TimeWithDayAttr(int rawValue) {
		super(rawValue);
	}
	
	public DayAttr getDayDivision (){
		
		if (this.v() >= TWO_DAYS_LATER_0000.v()) {
			return DayAttr.TWO_DAY_LATER;
		}
		if (this.v() >= THE_NEXT_DAY_0000.v()) {
			return DayAttr.THE_NEXT_DAY;
		}
		if (this.v() >= THE_PRESENT_DAY_0000.v()) {
			return DayAttr.THE_PRESENT_DAY;
		}
		
		return DayAttr.THE_PREVIOUS_DAY;
	}
	
	public int getDayTime(){
		return (Math.abs(this.v()) + MAX_MINUTES_IN_DAY) % MAX_MINUTES_IN_DAY;
	}
	
	/**
	 * Returns value as minutes integer.
	 * @return value as minutes integer
	 */
	public int valueAsMinutes() {
		return this.v();
	}
	
	public String getInDayTimeWithFormat(){
		return this.hour() + ":" + this.minute();
	}
	
	public String getRawTimeWithFormat(){
		return (this.v() / 60) + ":" + Math.abs((this.v() % 60));
	}
	
	public int hour() {
		return this.getDayTime() / 60;
	}

	public int minute() {
		return this.getDayTime() % 60;
	}

	public TimeWithDayAttr addHours(int hours) {
		return addTime(hours, Time.MAX_MS);
	}

	public TimeWithDayAttr addMinutes(int minutes) {
		return addTime(minutes, 1);
	}

	public TimeWithDayAttr minusHours(int hours) {
		return addTime(-hours, Time.MAX_MS);
	}

	public TimeWithDayAttr minusMinutes(int minutes) {
		return addTime(-minutes, 1);
	}
	
	/**
	 * Returns shifted time instance but min="12:00 at previous day" max="23:59 at two days later".
	 * @param minutesToShift
	 * @return shifted time instance
	 */
	public TimeWithDayAttr shiftWithLimit(int minutesToShift) {
		int newValue = this.v() + minutesToShift;
		newValue = Math.max(newValue, THE_PREVIOUS_DAY_1200.valueAsMinutes());
		newValue = Math.min(newValue, TWO_DAYS_LATER_2359.valueAsMinutes());
		return new TimeWithDayAttr(newValue);
	}
	
	private TimeWithDayAttr addTime(int value, int unitValue){
		int newValue = this.v() + value * unitValue;
		return new TimeWithDayAttr(newValue);
	}
}
