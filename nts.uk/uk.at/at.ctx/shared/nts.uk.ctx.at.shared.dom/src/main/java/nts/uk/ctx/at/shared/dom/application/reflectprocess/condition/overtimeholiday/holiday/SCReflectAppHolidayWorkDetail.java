package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday;

import nts.uk.ctx.at.shared.dom.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

/**
 * @author thanh_nx
 *
 *         休日出勤申請の反映（勤務予定）
 */
public class SCReflectAppHolidayWorkDetail {

	public static DailyAfterAppReflectResult process(Require require, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp, HdWorkAppReflect holidayWorkAppReflect) {

		// 休日出勤申請の反映（勤務予定）
		return SCBeforeReflectAppHolidayWork.process(require, holidayApp, dailyApp, holidayWorkAppReflect.getBefore());
	}

	public static interface Require extends SCBeforeReflectAppHolidayWork.Require {

	}
}
