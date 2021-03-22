package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.application.overtime.OvertimeApplicationSettingShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;

/**
 * @author thanh_nx
 *
 *         休出時間の反映
 */
public class ReflectHolidayDetail {

	public static List<Integer> process(OvertimeApplicationSettingShare applicationTime,
			DailyRecordOfApplication dailyApp, ReflectAppDestination reflectAppDes) {
		List<Integer> lstId = new ArrayList<Integer>();

		// 日別勤怠(work）の該当する[休出枠時間をチェック
		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()
				|| !dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
			return lstId;

		}

		Consumer<HolidayWorkFrameTime> holidayOverTime = new Consumer<HolidayWorkFrameTime>() {
			@Override
			public void accept(HolidayWorkFrameTime t) {
				// [input. 申請の反映先]をチェック
				if (reflectAppDes == ReflectAppDestination.SCHEDULE) {
					// 休出枠NOをキーにして休出時間をセットする
					t.setBeforeApplicationTime(Finally.of(applicationTime.getApplicationTime()));
					lstId.add(CancelAppStamp.createItemId(270, t.getHolidayFrameNo().v(), 5));
				} else {
					// // 休出枠NOをキーにして休出時間をセットする
					t.setHolidayWorkTime(
							Finally.of(TimeDivergenceWithCalculation.sameTime(applicationTime.getApplicationTime())));
					t.setTransferTime(Finally.of(TimeDivergenceWithCalculation.defaultValue()));
					lstId.add(CancelAppStamp.createItemId(266, t.getHolidayFrameNo().v(), 5));
					lstId.add(CancelAppStamp.createItemId(267, t.getHolidayFrameNo().v(), 5));
				}

			}
		};

		// 該当の枠NOをキーにした[休出枠時間]を作成する
		List<HolidayWorkFrameTime> holidayTimeWorkFrameTime = dailyApp.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
				.getWorkHolidayTime().get().getHolidayWorkFrameTime();

		Optional<HolidayWorkFrameTime> overTimeFrame = holidayTimeWorkFrameTime.stream()
				.filter(x -> x.getHolidayFrameNo().v() == applicationTime.getFrameNo().v()).findFirst();

		if (!overTimeFrame.isPresent()) {
			HolidayWorkFrameTime holidaytimeTemp = HolidayWorkFrameTime
					.createDefaultWithNo(applicationTime.getFrameNo().v());
			holidayOverTime.accept(holidaytimeTemp);
			holidayTimeWorkFrameTime.add(holidaytimeTemp);
		} else {
			holidayOverTime.accept(overTimeFrame.get());
		}

		// [input. 申請の反映先]をチェック

		// 休出枠NOをキーにして休出時間をセットする

		// 休出枠NOをキーにして休出時間をセットする

		return lstId;
	}
}
