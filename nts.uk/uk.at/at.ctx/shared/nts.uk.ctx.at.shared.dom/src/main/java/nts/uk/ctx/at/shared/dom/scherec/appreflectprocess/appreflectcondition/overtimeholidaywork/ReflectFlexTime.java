package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork;

import java.util.Arrays;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;

/**
 * @author thanh_nx
 *
 *         フレックス時間の反映
 */
public class ReflectFlexTime {

	// 事前フレックス時間を反映する
	public static DailyRecordOfApplication beforeReflectFlex(DailyRecordOfApplication dailyApp,
			AttendanceTimeOfExistMinus flexOverTime) {

		if (flexOverTime.v() <= 0) {
			return dailyApp;
		}

		// 事前フレックス時間を反映する
		if (dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()
				&& dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime()
					.setBeforeApplicationTime(new AttendanceTime(flexOverTime.v()));

			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(555));
		}

		return dailyApp;
	}

	// フレックス時間を反映する
	public static DailyRecordOfApplication reflectFlex(DailyRecordOfApplication dailyApp,
			AttendanceTimeOfExistMinus flexOverTime) {

		// フレックス時間を反映する
		if (dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()
				&& dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
			dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime()
					.replaceTimeAndCalcDiv(flexOverTime);

			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(556));
		}
		return dailyApp;
	}
}
