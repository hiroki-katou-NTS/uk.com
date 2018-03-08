package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 法定外深夜時間
 * @author keisuke_hoshina
 *
 */
@Value
public class ExcessOfStatutoryMidNightTime {
	private TimeWithCalculation time;
	private AttendanceTime beforeApplicationTime;
	
	public static ExcessOfStatutoryMidNightTime calcExcessTime(OverTimeOfDaily overDaily,HolidayWorkTimeOfDaily holidayDaily) {
		TimeWithCalculation overTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		TimeWithCalculation holidayTime = TimeWithCalculation.sameTime(new AttendanceTime(0));
		//残業深夜
		if(overDaily.getExcessOverTimeWorkMidNightTime().isPresent())
			overTime = overDaily.getExcessOverTimeWorkMidNightTime().get().getTime();
		
		//休出深夜
		if(holidayDaily.getHolidayMidNightWork().isPresent())
			holidayTime = holidayDaily.getHolidayMidNightWork().get().calcTotalTime();
		//return
		TimeWithCalculation totalTime = overTime.addMinutes(holidayTime.getTime(), holidayTime.getCalcTime());
		return new ExcessOfStatutoryMidNightTime(totalTime, new AttendanceTime(0));
		
	}
}
