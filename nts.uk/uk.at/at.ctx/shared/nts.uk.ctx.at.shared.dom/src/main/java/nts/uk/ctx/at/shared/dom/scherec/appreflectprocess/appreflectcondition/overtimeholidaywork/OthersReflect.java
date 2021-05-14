package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ReasonDivergenceShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceReasonContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 
 * 
 *  その他項目の反映
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OthersReflect extends DomainObject {
	/**
	 * 乖離理由を反映する
	 */
	private NotUseAtr reflectDivergentReasonAtr;

//    private NotUseAtr reflectOptionalItemsAtr;

	/**
	 * 加給時間を反映する
	 */
	private NotUseAtr reflectPaytimeAtr;

	/**
	 * @author thanh_nx
	 *
	 *         その他項目の反映
	 */

	public void process(ApplicationTimeShare overTimeApp, DailyRecordOfApplication dailyApp) {

		// [乖離理由を反映する]をチェック
		if (this.getReflectDivergentReasonAtr() == NotUseAtr.USE) {
			// 乖離理由の反映
			processReasonDissociation(dailyApp, overTimeApp.getReasonDissociation());
		}

		// [加給時間を反映する]をチェック
		if (this.getReflectPaytimeAtr() == NotUseAtr.USE) {
			// 加給時間の反映
			ReflectApplicationTime.process(overTimeApp.getApplicationTime(), dailyApp, Optional.empty());
		}

	}
	
	/**
	 *
	 *         乖離理由の反映
	 */
	private static void processReasonDissociation(DailyRecordOfApplication dailyApp,
			List<ReasonDivergenceShare> reasonDissociation) {

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
				divgTimeOpt.get().setDivResonCode(Optional.ofNullable(reason.getReasonCode()));
				divgTimeOpt.get().setDivReason(Optional.ofNullable(
						reason.getReason() == null ? null : new DivergenceReasonContent(reason.getReason().v())));
			} else {
				// 該当の勤務NOをキーにした[乖離時間]を作成する
				DivergenceTime timeTemp = DivergenceTime.createDefaultWithNo(reason.getDiviationTime());

				// 該当の[乖離理由]を日別勤怠(work）にセットする
				timeTemp.setDivResonCode(Optional.ofNullable(reason.getReasonCode()));
				timeTemp.setDivReason(Optional.ofNullable(
						reason.getReason() == null ? null : new DivergenceReasonContent(reason.getReason().v())));
				divgTimes.add(timeTemp);
			}

			lstId.add(CancelAppStamp.createItemId(438, reason.getDiviationTime(), 5));
			lstId.add(CancelAppStamp.createItemId(439, reason.getDiviationTime(), 5));

		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstId);

	}

}
