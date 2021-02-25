package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 社員別通常勤務法定労働時間
 */
@Getter
public class RegularLaborTimeSha extends WorkingTimeSetting {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	private String empId;

	private RegularLaborTimeSha(String comId, String empId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		super(comId, weeklyTime, dailyTime);
		
		this.empId = empId;
	}
	
	public static RegularLaborTimeSha of (String comId, String empId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		return new RegularLaborTimeSha(comId, empId, weeklyTime, dailyTime);
	}
}
