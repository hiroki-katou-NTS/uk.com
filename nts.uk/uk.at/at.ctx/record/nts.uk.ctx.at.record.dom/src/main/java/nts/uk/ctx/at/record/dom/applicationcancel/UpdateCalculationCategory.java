package nts.uk.ctx.at.record.dom.applicationcancel;

import nts.uk.ctx.at.shared.dom.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         計算区分の更新
 */
public class UpdateCalculationCategory {

	public static void updateCalc(IntegrationOfDaily daily, EngraveShareAtr appStampAtr) {

		if (appStampAtr == EngraveShareAtr.OVERTIME) {
			/** The normal ot time. */
			daily.getCalAttr().getOvertimeSetting().getNormalOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getOvertimeSetting().getNormalMidOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getOvertimeSetting().getLegalOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getOvertimeSetting().getLegalMidOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
		} else if (appStampAtr == EngraveShareAtr.EARLY) {
			daily.getCalAttr().getOvertimeSetting().getEarlyOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getOvertimeSetting().getEarlyMidOtTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);

		} else if (appStampAtr == EngraveShareAtr.HOLIDAY) {
			daily.getCalAttr().getHolidayTimeSetting().getRestTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
			daily.getCalAttr().getHolidayTimeSetting().getLateNightTime().setCalAtr(AutoCalAtrOvertime.CALCULATEMBOSS);
		}
	}
}
