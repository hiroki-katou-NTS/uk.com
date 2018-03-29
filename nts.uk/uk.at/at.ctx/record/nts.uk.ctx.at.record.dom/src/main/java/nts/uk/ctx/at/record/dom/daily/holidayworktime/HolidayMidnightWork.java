package nts.uk.ctx.at.record.dom.daily.holidayworktime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 休出深夜
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class HolidayMidnightWork {
	private List<HolidayWorkMidNightTime> holidayWorkMidNightTime;
	
	/**
	 * リストの時間を時間、計算時間毎に全て加算する
	 * @return
	 */
	public TimeDivergenceWithCalculation calcTotalTime() {
		AttendanceTime calcTime = new AttendanceTime(0);
		AttendanceTime time = new AttendanceTime(0);
		for(int listNo = 1 ; listNo <= this.holidayWorkMidNightTime.size() ; listNo++) {
			time = time.addMinutes(this.holidayWorkMidNightTime.get(listNo - 1).getTime().getTime().valueAsMinutes());
			calcTime = calcTime.addMinutes(this.holidayWorkMidNightTime.get(listNo - 1).getTime().getTime().valueAsMinutes());
		}
		return TimeDivergenceWithCalculation.createTimeWithCalculation(time, calcTime);
	}
}
