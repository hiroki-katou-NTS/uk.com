package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.support;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         応援の反映
 */
public class ReflectSupportProcess {

	public static List<Integer> reflect(Require require, AppStampShare application, DailyRecordOfApplication dailyApp,
			ReflectAppStamp reflectApp) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// [応援開始・終了を反映する]をチェック

		if (reflectApp.getReflectSupport() == NotUseAtr.NOT_USE) {
			return lstItemId;
		}
		// 応援開始・終了の反映
		lstItemId.addAll(ReflectSupportStartEnd.reflect(require, dailyApp,
				application.getListTimeStampApp().stream()
						.filter(x -> x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnumShare.CHEERING)
						.collect(Collectors.toList())));

		// 応援開始・終了打刻の取消
		lstItemId.addAll(CancelSupportStartEnd.process(dailyApp, application.getListDestinationTimeApp().stream()
				.filter(x -> x.getTimeStampAppEnum() == TimeStampAppEnumShare.CHEERING).collect(Collectors.toList())));

		return lstItemId;
	}

	public static interface Require extends ReflectSupportStartEnd.Require {

	}
}
