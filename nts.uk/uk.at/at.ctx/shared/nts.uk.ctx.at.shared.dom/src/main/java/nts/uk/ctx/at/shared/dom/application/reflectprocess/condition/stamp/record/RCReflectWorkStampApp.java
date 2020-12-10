package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.record;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.schedule.SCReflectWorkStampApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.support.ReflectSupportProcess;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;

/**
 * @author thanh_nx
 *
 *         打刻申請を反映する（勤務実績）
 */
public class RCReflectWorkStampApp {
	public static Collection<Integer> reflect(Require require, AppStampShare application,
			DailyRecordOfApplication dailyApp, ReflectAppStamp reflectApp) {

		Set<Integer> lstItemId = new HashSet<>();

		// [input. 打刻申請.事前事後区分]をチェック
		if (application.getPrePostAtr() == PrePostAtrShare.POSTERIOR) {
			// 事後
			// 打刻申請の反映
			lstItemId.addAll(SCReflectWorkStampApp.reflect(require, application, dailyApp, reflectApp));

			// 応援の反映
			lstItemId.addAll(ReflectSupportProcess.reflect(require, application, dailyApp, reflectApp));
		} else {
			// [事前]
			// 始業終業の反映
			lstItemId.addAll(ReflectStartEndWork.reflect(dailyApp,
					application.getListTimeStampAppOther().stream()
							.map(x -> new TimeZoneWithWorkNo(x.getDestinationTimeZoneApp().getEngraveFrameNo(),
									x.getTimeZone().getStartTime().v(), x.getTimeZone().getEndTime().v()))
							.collect(Collectors.toList()),
					PrePostAtrShare.PREDICT));
		}

		return lstItemId;
	}

	public static interface Require extends SCReflectWorkStampApp.Require, ReflectSupportProcess.Require {

	}
}
