package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday;

import nts.uk.ctx.at.shared.dom.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday.SCReflectAppHolidayWorkDetail;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;

/**
 * @author thanh_nx
 *
 *         休日出勤申請を反映する（勤務予定）
 */
public class SCReflectAppHolidayWork {

	public static DailyAfterAppReflectResult process(Require require, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp, AppReflectOtHdWork reflectAppSet) {

		// 休日出勤申請の反映（勤務予定）
		return SCReflectAppHolidayWorkDetail.process(require, holidayApp, dailyApp,
				reflectAppSet.getHolidayWorkAppReflect());
	}

	public static interface Require extends SCReflectAppHolidayWorkDetail.Require {

	}
}
