package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance;

import java.util.Arrays;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.TimeVacation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeVacationHelper {
	/**
	 * createTimeVacation
	 * @param start 開始時間1
	 * @param end1  終了時間1
	 * @param timePaidUseTime 使用時間
	 * @return
	 */
	public static TimeVacation createTimeVacation(TimeWithDayAttr start1, TimeWithDayAttr end1,
			TimevacationUseTimeOfDaily timePaidUseTime) {
		return new TimeVacation(Arrays.asList(
				new TimeSpanForCalc(start1, end1)), timePaidUseTime);
	}
	
	/**
	 * createTimeVacations
	 * @param start1 開始時間1
	 * @param end1  終了時間1
	 * @param start2 開始時間2
	 * @param end2  終了時間2
	 * @param timePaidUseTime 使用時間
	 * @return
	 */
	public static TimeVacation createTimeVacations(
			TimeWithDayAttr start1, TimeWithDayAttr end1,
			TimeWithDayAttr start2, TimeWithDayAttr end2,
			TimevacationUseTimeOfDaily timePaidUseTime) {
		return new TimeVacation(Arrays.asList(
				new TimeSpanForCalc(start1, end1),
				new TimeSpanForCalc(start2, end2)),
				timePaidUseTime);
	}
}
