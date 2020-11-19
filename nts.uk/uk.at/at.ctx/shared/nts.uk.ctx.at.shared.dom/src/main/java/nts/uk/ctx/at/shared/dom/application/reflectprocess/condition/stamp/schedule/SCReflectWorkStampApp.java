package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.schedule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.CancelTimeZoneApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAttendanceLeav;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectBreakTime;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectOutingTimeZone;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectShortTime;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectTemporaryAttLeav;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeZoneStampClassificationShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         打刻申請の反映
 */
public class SCReflectWorkStampApp {

	public static Collection<Integer> reflect(Require require, AppStampShare application,
			DailyRecordOfApplication dailyApp, ReflectAppStamp reflectApp) {

		Set<Integer> lstItemId = new HashSet<>();
		// [出退勤を反映する]をチェック

		if (reflectApp.getReflectAttLeav() == NotUseAtr.USE) {
			// 出退勤の反映
			lstItemId.addAll(ReflectAttendanceLeav.reflect(dailyApp,
					application.getListTimeStampApp().stream()
							.filter(x -> x.getDestinationTimeApp()
									.getTimeStampAppEnum() == TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT)
							.collect(Collectors.toList())));

			// 時刻申請の取消
			lstItemId.addAll(CancelAppStamp.process(dailyApp,
					application.getListDestinationTimeApp().stream()
							.filter(x -> x.getTimeStampAppEnum() == TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT)
							.collect(Collectors.toList())));
		}

		// [臨時出退勤を反映する]をチェック

		if (reflectApp.getReflectTemporary() == NotUseAtr.USE) {
			// 臨時出退勤の反映
			ReflectTemporaryAttLeav.reflect(dailyApp,
					application.getListTimeStampApp().stream().filter(
							x -> x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnumShare.EXTRAORDINARY)
							.collect(Collectors.toList()));

			// 時刻申請の取消
			lstItemId.addAll(CancelAppStamp.process(dailyApp,
					application.getListDestinationTimeApp().stream()
							.filter(x -> x.getTimeStampAppEnum() == TimeStampAppEnumShare.EXTRAORDINARY)
							.collect(Collectors.toList())));

		}

		// [育児時間帯を反映する]をチェック

		if (reflectApp.getReflectChildCare() == NotUseAtr.USE) {
			// 短時間勤務時間帯の反映
			lstItemId
					.addAll(ReflectShortTime.reflect(dailyApp,
							application.getListTimeStampAppOther().stream().filter(x -> x.getDestinationTimeZoneApp()
									.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.PARENT)
									.collect(Collectors.toList())));

			// 時間帯申請の取消
			lstItemId.addAll(CancelTimeZoneApplication.process(dailyApp,
					application.getListDestinationTimeZoneApp().stream()
							.filter(x -> x.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.PARENT)
							.collect(Collectors.toList())));
		}

		// [介護時間帯を反映する]をチェック

		if (reflectApp.getReflectCare() == NotUseAtr.USE) {
			// 短時間勤務時間帯の反映
			lstItemId.addAll(ReflectShortTime.reflect(dailyApp,
					application.getListTimeStampAppOther().stream()
							.filter(x -> x.getDestinationTimeZoneApp()
									.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.NURSE)
							.collect(Collectors.toList())));

			// 時間帯申請の取消
			lstItemId.addAll(CancelTimeZoneApplication.process(dailyApp,
					application.getListDestinationTimeZoneApp().stream()
							.filter(x -> x.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.NURSE)
							.collect(Collectors.toList())));
		}

		// [外出時間帯を反映する]をチェック

		if (reflectApp.getReflectGoOut() == NotUseAtr.USE) {
			// 外出時間帯の反映
			lstItemId.addAll(ReflectOutingTimeZone.process(dailyApp, application.getListTimeStampApp().stream().filter(
					x -> x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnumShare.GOOUT_RETURNING)
					.collect(Collectors.toList())));

			// 時刻申請の取消
			lstItemId.addAll(CancelAppStamp.process(dailyApp,
					application.getListDestinationTimeApp().stream()
							.filter(x -> x.getTimeStampAppEnum() == TimeStampAppEnumShare.GOOUT_RETURNING)
							.collect(Collectors.toList())));
		}

		// [休憩時間帯を反映する]をチェック

		if (reflectApp.getReflectBreakTime() == NotUseAtr.USE) {
			// 休憩時間帯の反映
			lstItemId.addAll(ReflectBreakTime.reflect(dailyApp,
					application.getListTimeStampAppOther().stream()
							.filter(x -> x.getDestinationTimeZoneApp()
									.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.BREAK)
							.collect(Collectors.toList())));

			// 時間帯申請の取消
			lstItemId.addAll(CancelTimeZoneApplication.process(dailyApp,
					application.getListDestinationTimeZoneApp().stream()
							.filter(x -> x.getTimeZoneStampClassification() == TimeZoneStampClassificationShare.BREAK)
							.collect(Collectors.toList())));
		}
		return lstItemId;
	}

	public static interface Require {

	}
}
