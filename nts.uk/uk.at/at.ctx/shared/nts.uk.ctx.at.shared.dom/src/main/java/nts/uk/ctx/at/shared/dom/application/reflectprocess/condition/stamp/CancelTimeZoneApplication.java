package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeZoneAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeZoneStampClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;

/**
 * @author thanh_nx
 *
 *         時間帯申請の取消
 */
public class CancelTimeZoneApplication {

	public static List<Integer> process(DailyRecordOfApplication dailyApp,
			List<DestinationTimeZoneAppShare> listDestinationTimeZoneApp) {

		List<Integer> lstItemId = new ArrayList<>();
		// [input. 打刻取消(List)]でループ
		listDestinationTimeZoneApp.stream().forEach(data -> {

			if (data.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.BREAK) {
				// 処理中の打刻枠NOの休憩時間帯をクリアする
				dailyApp.getBreakTime().stream().filter(x -> x.getBreakType() == BreakType.REFER_WORK_TIME)
						.forEach(x -> {
							x.getBreakTimeSheets().stream()
									.filter(y -> y.getBreakFrameNo().v() == data.getEngraveFrameNo().intValue())
									.map(y -> {
										y.setStartTime(null);
										y.setEndTime(null);
//							※日別勤怠(work）.予定実績区分＝[実績]の場合のみ
//									　　日別勤怠の休憩時間帯.時間帯.開始.時刻変更理由.時刻変更手段 ← 1:申請
//									　　日別勤怠の休憩時間帯.時間帯.終了.時刻変更理由.時刻変更手段 ← 1:申請
// 							domain ko co truong day can xu ly
										lstItemId.addAll(createItemId(data));
										return y;
									}).collect(Collectors.toList());
						});
			} else {
				dailyApp.getShortTime().ifPresent(x -> {
					// 処理中の打刻枠NOがキーとなる[短時間勤務時間帯]を削除する
					x.getShortWorkingTimeSheets()
							.removeIf(y -> y.getShortWorkTimeFrameNo().v() == data.getEngraveFrameNo().intValue());
					lstItemId.addAll(createItemId(data));
				});
			}
		});
		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

	private static List<Integer> createItemId(DestinationTimeZoneAppShare data) {
		List<Integer> lstItemId = new ArrayList<>();
		if (data.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.PARENT) {
			lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(759, data.getEngraveFrameNo(), 2),
					CancelAppStamp.createItemId(760, data.getEngraveFrameNo(), 2)));
		} else if (data.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.NURSE) {
			lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(763, data.getEngraveFrameNo(), 2),
					CancelAppStamp.createItemId(764, data.getEngraveFrameNo(), 2)));
		} else {
			lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(7, data.getEngraveFrameNo(), 2),
					CancelAppStamp.createItemId(8, data.getEngraveFrameNo(), 2)));
		}
		return lstItemId;
	}

}
