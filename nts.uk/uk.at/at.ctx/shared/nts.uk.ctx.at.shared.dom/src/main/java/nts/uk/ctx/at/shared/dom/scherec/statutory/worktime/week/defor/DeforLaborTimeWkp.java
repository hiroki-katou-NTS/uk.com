package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 職場別変形労働法定労働時間
 */
@Getter
public class DeforLaborTimeWkp extends WorkingTimeSetting {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 職場ID */
	private String workplaceId;

	private DeforLaborTimeWkp(String comId, String workplaceId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		super(comId, weeklyTime, dailyTime);
		
		this.workplaceId = workplaceId;
	}
	
	public static DeforLaborTimeWkp of (String comId, String workplaceId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		return new DeforLaborTimeWkp(comId, workplaceId, weeklyTime, dailyTime);
	}
}
