package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 会社別通常勤務法定労働時間
 */
@Getter
public class RegularLaborTimeCom extends WorkingTimeSetting {

	/***/
	private static final long serialVersionUID = 1L;

	private RegularLaborTimeCom(String comId, WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		super(comId, weeklyTime, dailyTime);
	}
	
	public static RegularLaborTimeCom of (String comId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		return new RegularLaborTimeCom(comId, weeklyTime, dailyTime);
	}
}
