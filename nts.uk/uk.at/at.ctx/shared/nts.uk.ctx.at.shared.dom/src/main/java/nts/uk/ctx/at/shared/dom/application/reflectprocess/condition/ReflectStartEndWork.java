package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;

/**
 * @author thanh_nx
 *
 *         始業終業の反映
 */
public class ReflectStartEndWork {

	public static List<Integer> reflect(DailyRecordOfApplication dailyApp,
			List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst, PrePostAtrShare prePostAtr) {
		List<Integer> itemIds = new ArrayList<Integer>();
		// [input:事前事後区分]をチェック
		if (prePostAtr == PrePostAtrShare.PREDICT) {
			// 日別勤怠(work)の予定勤務時間帯に[input:申請勤務時間帯]をセットする
			timeZoneWithWorkNoLst.stream().forEach(timeZone -> {
				Optional<ScheduleTimeSheet> timeSheet = dailyApp.getWorkInformation().getScheduleTimeSheets().stream()
						.filter(x -> {
							return timeZone.getWorkNo().v() == x.getWorkNo().v();
						}).findFirst();
				timeSheet.ifPresent(x -> {
					if (timeZone.getWorkNo().v() == 1) {
						itemIds.addAll(Arrays.asList(3, 4));
					} else {
						itemIds.addAll(Arrays.asList(5, 6));
					}
					x.setAttendance(timeZone.getTimeZone().getStartTime());
					x.setLeaveWork(timeZone.getTimeZone().getEndTime());
				});

			});
		}

		// 勤怠項目ID一覧 = 反映した項目のみに該当する勤怠項目ID
		// セットした勤務NOに対応する[始業１～2]、[終業1～2]
		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, itemIds);
		return itemIds;
	}

}
