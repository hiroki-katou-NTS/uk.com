package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 会社別変形労働法定労働時間
 */
@Getter
public class DeforLaborTimeCom extends WorkingTimeSetting {

	/***/
	private static final long serialVersionUID = 1L;

	private DeforLaborTimeCom(String comId, WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		super(comId, weeklyTime, dailyTime);
	}
	
	public static DeforLaborTimeCom of (String comId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		return new DeforLaborTimeCom(comId, weeklyTime, dailyTime);
	}
}
