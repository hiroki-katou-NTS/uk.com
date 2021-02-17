package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import nts.uk.ctx.at.shared.dom.application.overtime.OvertimeApplicationSettingShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;

/**
 * @author thanh_nx
 *
 *         残業時間の反映
 */
public class ReflectOvertimeDetail {

	public static List<Integer> process(OvertimeApplicationSettingShare applicationTime,
			DailyRecordOfApplication dailyApp, ReflectAppDestination reflectAppDes) {

		List<Integer> lstId = new ArrayList<Integer>();
		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()
				|| !dailyApp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
			return lstId;

		}

		List<OverTimeFrameTime> overTimeWorkFrameTime = dailyApp.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork()
				.get().getOverTimeWorkFrameTime();

		Consumer<OverTimeFrameTime> consumerOverTime = new Consumer<OverTimeFrameTime>() {
			@Override
			public void accept(OverTimeFrameTime t) {
				// [input. 申請の反映先]をチェック
				if (reflectAppDes == ReflectAppDestination.SCHEDULE) {
					// 残業枠NOをキーにして事前残業時間をセットする
					t.setBeforeApplicationTime(applicationTime.getApplicationTime());
					lstId.add(CancelAppStamp.createItemId(220, t.getOverWorkFrameNo().v(), 5));
				} else {
					// 残業枠NOをキーにして残業時間をセットする
					t.setOverTimeWork(TimeDivergenceWithCalculation.sameTime(applicationTime.getApplicationTime()));
					t.setTransferTime(TimeDivergenceWithCalculation.defaultValue());
					lstId.add(CancelAppStamp.createItemId(216, t.getOverWorkFrameNo().v(), 5));
					lstId.add(CancelAppStamp.createItemId(217, t.getOverWorkFrameNo().v(), 5));
				}

			}
		};

		// 該当の枠NOをキーにした[残業枠時間]を作成する
		Optional<OverTimeFrameTime> overTimeFrame = overTimeWorkFrameTime.stream()
				.filter(x -> x.getOverWorkFrameNo().v() == applicationTime.getFrameNo().v()).findFirst();

		if (!overTimeFrame.isPresent()) {
			OverTimeFrameTime overtimeTemp = OverTimeFrameTime.createDefaultWithNo(applicationTime.getFrameNo().v());
			consumerOverTime.accept(overtimeTemp);
			overTimeWorkFrameTime.add(overtimeTemp);
		} else {
			consumerOverTime.accept(overTimeFrame.get());
		}

		return lstId;
	}

}
