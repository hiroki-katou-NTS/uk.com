package nts.uk.shr.com.primitive;

import java.lang.reflect.Constructor;
import java.util.Optional;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.PrimitiveValueConstraintException;
import nts.arc.primitive.TimePrimitiveValue;
import nts.arc.primitive.constraint.LongRange;
import nts.gul.util.Time;
import nts.uk.shr.com.enumcommon.DayAttr;


@LongRange(max = 4319, min = -720)
public class TimeWithDayAttr extends IntegerPrimitiveValue<TimeWithDayAttr>{
	
	private final DayAttr dayDivision; 
	
	private final int time; 
	
	private static final long serialVersionUID = 1L;

	public TimeWithDayAttr(int rawValue) {
		super(rawValue);
		if (rawValue < 0){
			dayDivision = DayAttr.THE_PREVIOUS_DAY;
			time = Math.abs(rawValue);
		} else {
			dayDivision = checkDayDivision(rawValue);
			time = getTimeDuration(rawValue);
		}
	}
	
	public DayAttr getDayDivision (){
		return this.dayDivision;
	}
	
	public int getDayTime(){
		return time;
	}
	
	public String getInDayTimeWithFormat(){
		return (time / 60) + ":" + (time % 60);
	}
	
	public String getRawTimeWithFormat(){
		return (this.v() / 60) + ":" + (this.v() % 60);
	}
	
	public int hour() {
		return Time.getHour(this.time).intValue();
	}

	public int minute() {
		return Time.getMinute(this.time).intValue();
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
	
	private TimeWithDayAttr addTime(int value, int unitValue){
		int newValue = this.v() + value * unitValue;
		return new TimeWithDayAttr(newValue);
	}
	
	private int getTimeDuration(int value){
		if(this.dayDivision == DayAttr.THE_PRESENT_DAY){
			return value;
		}
		return value - (this.dayDivision.value -1) * Time.MAX_HOUR * Time.MAX_MS;
	}

	private DayAttr checkDayDivision(int value) {
		int days = value / (Time.MAX_HOUR*Time.MAX_MS);
		switch (days) {
			case 0:
				return DayAttr.THE_PRESENT_DAY;
			case 1:
				return DayAttr.THE_NEXT_DAY;
			case 2:
				return DayAttr.TWO_DAY_LATER;
			default:
				throw new PrimitiveValueConstraintException();
		}
	}
}
