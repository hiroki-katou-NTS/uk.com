package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.otheritem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.overtime.ReasonDivergenceShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceReasonContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceTime;

/**
 * @author thanh_nx
 *
 *         乖離理由の反映
 */
public class ReflectReasonDissociation {

	public static void process(DailyRecordOfApplication dailyApp, List<ReasonDivergenceShare> reasonDissociation) {

		List<Integer> lstId = new ArrayList<Integer>();
		if (!dailyApp.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return;
		}
		// [input. 乖離理由(List）]でループ
		reasonDissociation.stream().forEach(reason -> {
			// [日別勤怠(work）の乖離時間]をチェック
			List<DivergenceTime> divgTimes = dailyApp.getAttendanceTimeOfDailyPerformance().get()
					.getActualWorkingTimeOfDaily().getDivTime().getDivergenceTime();

			Optional<DivergenceTime> divgTimeOpt = divgTimes.stream()
					.filter(x -> x.getDivTimeId() == reason.getDiviationTime()).findFirst();
			if (divgTimeOpt.isPresent()) {
				// 該当の[乖離理由]を日別勤怠(work）にセットする
				divgTimeOpt.get().setDivResonCode(Optional.of(reason.getReasonCode()));
				divgTimeOpt.get().setDivReason(Optional.of(new DivergenceReasonContent(reason.getReason().v())));
			} else {
				// 該当の勤務NOをキーにした[乖離時間]を作成する
				DivergenceTime timeTemp = DivergenceTime.createDefaultWithNo(reason.getDiviationTime());

				// 該当の[乖離理由]を日別勤怠(work）にセットする
				timeTemp.setDivResonCode(Optional.of(reason.getReasonCode()));
				timeTemp.setDivReason(Optional.of(new DivergenceReasonContent(reason.getReason().v())));
				divgTimes.add(timeTemp);
			}

			lstId.add(CancelAppStamp.createItemId(438, reason.getDiviationTime(), 5));
			lstId.add(CancelAppStamp.createItemId(439, reason.getDiviationTime(), 5));

		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstId);

	}

}
