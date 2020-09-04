package nts.uk.ctx.at.record.dom.applicationcancel;

import nts.uk.ctx.at.shared.dom.application.stamp.AppStampCombinationAtrShare;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;

/**
 * @author thanh_nx
 *
 *         計算区分の更新
 */
public class UpdateCalculationCategory {

	public static void updateCalc(IntegrationOfDaily daily, AppStampCombinationAtrShare appStampAtr) {

		if (appStampAtr == AppStampCombinationAtrShare.OVERTIME) {
			/** The normal ot time. */
			daily.getCalAttr().getOvertimeSetting().getNormalOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getOvertimeSetting().getNormalMidOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getOvertimeSetting().getLegalOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getOvertimeSetting().getLegalMidOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
		} else if (appStampAtr == AppStampCombinationAtrShare.EARLY) {
			daily.getCalAttr().getOvertimeSetting().getEarlyOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getOvertimeSetting().getEarlyMidOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);

		} else if (appStampAtr == AppStampCombinationAtrShare.HOLIDAY) {
			daily.getCalAttr().getHolidayTimeSetting().getRestTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getHolidayTimeSetting().getLateNightTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
		}
	}
}
