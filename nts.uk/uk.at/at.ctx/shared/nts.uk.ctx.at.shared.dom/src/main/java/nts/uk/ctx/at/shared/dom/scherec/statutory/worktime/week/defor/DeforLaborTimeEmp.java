package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 雇用別変形労働法定労働時間
 */
@Getter
public class DeforLaborTimeEmp extends WorkingTimeSetting {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 雇用コード */
	private EmploymentCode employmentCode;

	private DeforLaborTimeEmp(String comId, EmploymentCode employmentCode, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		super(comId, weeklyTime, dailyTime);
		
		this.employmentCode = employmentCode;
	}
	
	public static DeforLaborTimeEmp of (String comId, EmploymentCode employmentCode, 
			WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		
		return new DeforLaborTimeEmp(comId, employmentCode, weeklyTime, dailyTime);
	}
}
