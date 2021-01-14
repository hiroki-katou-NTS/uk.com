package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;

public class ActualWorkingTimeOfDailyDto {
	// 割増時間
	private PremiumTimeOfDailyPerformanceDto premiumTimeOfDailyPerformance;

	// 拘束差異時間
	private Integer constraintDifferenceTime;

	// 拘束時間
	private ConstraintTimeDto constraintTime;

	// 時差勤務時間
	private Integer timeDifferenceWorkingHours;

	// 総労働時間
	private TotalWorkingTimeDto totalWorkingTime;

//	// 乖離時間
//	private DivergenceTimeOfDailyDto divTime;
}
