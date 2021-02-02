package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.workplace;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 
 * @author sonnlb
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（廃止★）.職場.職場別通常勤務月間労働時間
 */
public class RegularWorkingMonthsByWorkplace extends WorkingTimeSetting {

	protected RegularWorkingMonthsByWorkplace(String comId, WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		super(comId, weeklyTime, dailyTime);
		// TODO Auto-generated constructor stub
	}

}
