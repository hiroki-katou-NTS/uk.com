package nts.uk.file.at.infra.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.enumcommon.DayAttr;

@Data
@AllArgsConstructor
public class TimeDurationFormatExtend {
	/** minutes of day */
	public static final int MINUTES_OF_DAY = 24 * 60;
	
	public int value;
	
	public Integer reviseRawValue(Integer rawValue) {
		if(rawValue > 4319)
			rawValue = 4319;
		if(-1440 > rawValue)
			rawValue = -1440;
		return rawValue;
	}
	
	/**
	 * Returns true if this is negative time.
	 * @return true if this is negative time
	 */
	public boolean isNegative() {
		return this.value < 0;
	}
	
	public int daysOffset() {
		return this.isNegative()
				? (this.value + 1) / MINUTES_OF_DAY - 1
				: this.value / MINUTES_OF_DAY;
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
			throw new RuntimeException("not supported day attr: " + value);
		}
	}
	
	public String getInDayTimeWithFormat(){
		String g = (int) this.hour() + ":" + (this.minute() < 10 ? "0" + this.minute() : this.minute());
		return g;
	}
	
	public String getFullText() {
		// とりあえず日本語のみに対応
		// 英語等の場合にどうなるか、まだ仕様が決まっていない
		return this.dayAttr().description + this.getInDayTimeWithFormat();
	}
	
	public double hour() {
		double dVal = value;
		return isNegative()? 24 - Math.ceil(Math.abs(dVal)/60) : Math.floor(dVal / 60) % 24;
	}
	
	public int rawHour(){
		return value / 60;
	}
	
	public int minute() {
		int m = isNegative()? (value % 60) % 60 : value % 60;
		return Math.abs(m);
	}
	
	public String getTimeText() {
		return (isNegative()? "-" + (int) Math.abs(this.rawHour()) : (int) Math.abs(this.rawHour()) ) + ":" + (this.minute() < 10 ? "0" + this.minute() : this.minute());
	}
}
