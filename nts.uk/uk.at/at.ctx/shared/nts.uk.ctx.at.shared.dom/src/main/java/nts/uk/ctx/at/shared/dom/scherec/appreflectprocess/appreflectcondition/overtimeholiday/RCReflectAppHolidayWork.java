package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday.RCReflectAppHolidayWorkDetail;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         休日出勤申請を反映する（勤務実績）
 */
public class RCReflectAppHolidayWork {

	public static DailyAfterAppReflectResult process(Require require, String cid, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp, AppReflectOtHdWork reflectAppSet) {
		List<Integer> lstId = new ArrayList<Integer>();
		// 休日出勤申請の反映
		lstId.addAll(RCReflectAppHolidayWorkDetail
				.process(require, cid, holidayApp, dailyApp, reflectAppSet.getHolidayWorkAppReflect()).getLstItemId());

		// [時間外深夜時間を反映する]をチェック
		if (reflectAppSet.getNightOvertimeReflectAtr() == NotUseAtr.USE) {
			// 時間外深夜時間の反映
			ReflectLateNightHolidayWork.process(dailyApp, holidayApp.getApplicationTime(), holidayApp.getPrePostAtr());
		}

		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	public static interface Require extends RCReflectAppHolidayWorkDetail.Require {

	}
}
