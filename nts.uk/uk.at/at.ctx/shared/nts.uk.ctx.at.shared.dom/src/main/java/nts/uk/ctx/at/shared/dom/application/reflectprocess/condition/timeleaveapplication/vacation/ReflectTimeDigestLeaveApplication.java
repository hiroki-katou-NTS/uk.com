package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication.vacation;

import java.util.Arrays;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;

/**
 * @author thanh_nx
 *
 *         休暇申請の時間消化の反映
 */
public class ReflectTimeDigestLeaveApplication {

	public static DailyRecordOfApplication process(TimeDigestApplicationShare timeDigestApp,
			DailyRecordOfApplication dailyApp, TimeLeaveAppReflectCondition condition) {

		// [input.時間消化申請(work）.時間年休]を日別勤怠(work）へセット
		dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
			x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getAnnual()
					.setDigestionUseTime(timeDigestApp.getTimeAnnualLeave());
		});

		// [input.時間消化申請(work）.時間代休]を日別勤怠(work）へセット
		dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
			x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getSubstitute()
					.setDigestionUseTime(timeDigestApp.getTimeOff());
		});

		// [input.時間消化申請(work）.時間特別休暇]を日別勤怠(work）へセット
		dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
			x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getSpecialHoliday()
					.setDigestionUseTime(timeDigestApp.getTimeSpecialVacation());
		});

		// [input.時間消化申請(work）.60H超休]を日別勤怠(work）へセット
		dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
			x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getOverSalary()
					.setDigestionUseTime(timeDigestApp.getTimeSpecialVacation());
		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(539, 541, 543, 545));

		return dailyApp;
	}
}
