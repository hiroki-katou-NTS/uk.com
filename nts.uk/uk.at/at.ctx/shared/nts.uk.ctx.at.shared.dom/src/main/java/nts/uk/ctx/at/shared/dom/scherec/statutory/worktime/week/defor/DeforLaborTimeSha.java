package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 社員別変形労働法定労働時間
 */
@Getter
public class DeforLaborTimeSha extends WorkingTimeSetting {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	private String empId;

	private DeforLaborTimeSha(String comId, String empId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		super(comId, weeklyTime, dailyTime);
		
		this.empId = empId;
	}
	
	public static DeforLaborTimeSha of (String comId, String empId, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		return new DeforLaborTimeSha(comId, empId, weeklyTime, dailyTime);
	}
}
