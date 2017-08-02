package nts.uk.shr.com.primitive;

import java.lang.reflect.Constructor;
import java.util.Optional;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.PrimitiveValueConstraintException;
import nts.arc.primitive.TimePrimitiveValue;
import nts.arc.primitive.constraint.LongRange;
import nts.gul.util.Time;
import nts.uk.shr.com.enumcommon.DayDivision;


@LongRange(max = 4319, min = -720)
public class TimeWithDayAttr extends LongPrimitiveValue<TimeWithDayAttr> implements TimePrimitiveValue<TimeWithDayAttr>{
	
	private final DayDivision dayDivision; 
	
	private final Long time; 
	
	private static final long serialVersionUID = 1L;

	public TimeWithDayAttr(Long rawValue) {
		super(rawValue);
		if (rawValue < 0){
			dayDivision = DayDivision.THE_PREVIOUS_DAY;
			time = Math.abs(rawValue);
		} else {
			dayDivision = checkDayDivision(rawValue);
			time = getTimeDuration(rawValue);
		}
	}
	
	public DayDivision getDayDivision (){
		return this.dayDivision;
	}
	
	public Long getDayTime(){
		return time;
	}
	
	public String getInDayTimeWithFormat(){
		return (time / 60) + ":" + (time % 60);
	}
	
	public String getRawTimeWithFormat(){
		return (this.v() / 60) + ":" + (this.v() % 60);
	}

	@Override
	public int hour() {
		return Time.getHour(this.time).intValue();
	}

	@Override
	public int minute() {
		return Time.getMinute(this.time).intValue();
	}

	@Override
	public int second() {
		return 0;
	}

	@Override
	public double hours() {
		return Time.toHours(this.time);
	}

	@Override
	public double minutes() {
		return Time.toMinutes(this.time);
	}

	@Override
	public TimePrimitiveValue<TimeWithDayAttr> addHours(int hours) {
		return addTime(hours, Time.MAX_MS);
	}

	@Override
	public TimePrimitiveValue<TimeWithDayAttr> addMinutes(int minutes) {
		return addTime(minutes, 1);
	}

	@Override
	public TimePrimitiveValue<TimeWithDayAttr> minusHours(int hours) {
		return addTime(-hours, Time.MAX_MS);
	}

	@Override
	public TimePrimitiveValue<TimeWithDayAttr> minusMinutes(int minutes) {
		return addTime(-minutes, 1);
	}

	@Override
	@Deprecated
	public TimePrimitiveValue<TimeWithDayAttr> addSeconds(int seconds) {
		return null;
	}

	@Override
	@Deprecated
	public TimePrimitiveValue<TimeWithDayAttr> minusSeconds(int seconds) {
		return null;
	}

	@Override
	@Deprecated
	public TimePrimitiveValue<TimeWithDayAttr> calculate(int value, long coeff) {
		return null;
	}

	@Override
	@Deprecated
	public Optional<Constructor<TimePrimitiveValue<TimeWithDayAttr>>> getConstructor(Class<?>... types) {
		return TimePrimitiveValue.super.getConstructor(types);
	}
	
	private TimePrimitiveValue<TimeWithDayAttr> addTime(int value, int unitValue){
		Long newValue = this.v() + value * unitValue;
		return new TimeWithDayAttr(newValue);
	}
	
	private Long getTimeDuration(Long value){
		if(this.dayDivision == DayDivision.THE_PRESENT_DAY){
			return value;
		}
		return value - (this.dayDivision.value -1) * Time.MAX_HOUR * Time.MAX_MS;
	}

	private DayDivision checkDayDivision(Long value) {
		Long days = value / (Time.MAX_HOUR*Time.MAX_MS);
		switch (days.intValue()) {
			case 0:
				return DayDivision.THE_PRESENT_DAY;
			case 1:
				return DayDivision.THE_NEXT_DAY;
			case 2:
				return DayDivision.TWO_DAY_LATER;
			default:
				throw new PrimitiveValueConstraintException();
		}
	}
}
