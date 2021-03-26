package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.holiday.SCReflectAppHolidayWorkDetail;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;

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
