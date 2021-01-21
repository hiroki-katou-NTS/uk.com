package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 職場別通常勤務法定労働時間
 */
@Getter
public class RegularLaborTimeWkp extends WorkingTimeSetting {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 職場ID */
	private String workplaceId;

	private RegularLaborTimeWkp(String comId, String workplaceId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		super(comId, weeklyTime, dailyTime);
		
		this.workplaceId = workplaceId;
	}
	
	public static RegularLaborTimeWkp of (String comId, String workplaceId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		return new RegularLaborTimeWkp(comId, workplaceId, weeklyTime, dailyTime);
	}
}
