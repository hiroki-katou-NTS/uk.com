package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;

/**
 * @author thanh_nx
 *
 *         残業時間の代休振替
 */
public class TranferOvertimeCompensatory {

	public static void process(Require require, String cid, IntegrationOfDaily dailyRecord) {

		// 最大の時間帯でworkを作成
		IntegrationOfDaily dailyRecordNew = CreateWorkMaxTimeZone.process(require, cid, dailyRecord);

		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		List<MaximumTime> maxTime = new ArrayList<>();
		List<MaximumTime> timeAfterReflectApp = new ArrayList<>();
		if (!dailyRecordNew.getAttendanceTimeOfDailyPerformance().isPresent()
				|| dailyRecordNew.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
			return ;
		}

		OverTimeOfDaily overTimeWork = dailyRecordNew.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork()
				.get();

		// 計算日別勤怠(wrok）から残業枠時間帯（List）の内容を移送し、[最大時間帯]へセットする
		maxTimeZone.getTimeSpan().putAll(overTimeWork.getOverTimeWorkFrameTimeSheet().stream()
				.collect(Collectors.toMap(x -> x.getFrameNo().v(), x -> x.getTimeSpan())));

		// 計算日別勤怠(work）から残業枠時間（List）の内容を移送して、[最大の時間]を作成する
		maxTime.addAll(overTimeWork.getOverTimeWorkFrameTime().stream().map(x -> {
			return new MaximumTime(x.getOverWorkFrameNo().v(), x.getOverTimeWork().getCalcTime(),
					x.getTransferTime().getCalcTime());
		}).collect(Collectors.toList()));

		// input.日別勤怠(work）から残業枠時間（List）の内容を移送し、[申請を反映させた後の時間]へセットする
		timeAfterReflectApp.addAll(overTimeWork.getOverTimeWorkFrameTime().stream().map(x -> {
			return new MaximumTime(x.getOverWorkFrameNo().v(), x.getOverTimeWork().getTime(),
					x.getTransferTime().getTime());
		}).collect(Collectors.toList()));

		// 代休振替処理
		List<MaximumTime> lstMaxTime = SubstituteTransferProcess.process(maxTimeZone, maxTime, timeAfterReflectApp);

		// [振替をした後の時間(List）]のすべての内容を[日別勤怠(work）]にセットする
		dailyRecordNew.getAttendanceTimeOfDailyPerformance().get()
		.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork()
		.ifPresent(overtimeOld -> {
			overtimeOld.getOverTimeWorkFrameTime().stream().map(x -> {
				val maxTimeTemp = lstMaxTime.stream().filter(y -> y.getNo() == x.getOverWorkFrameNo().v()).findFirst();
				if (!maxTimeTemp.isPresent()) {
					return x;
				}
				x.getOverTimeWork().setTime(maxTimeTemp.get().getTime());
				x.getTransferTime().setTime(maxTimeTemp.get().getTransferTime());
				return x;
			}).collect(Collectors.toList());
		});

		return ;

	}

	public static interface Require extends CreateWorkMaxTimeZone.Require {

	}
}
