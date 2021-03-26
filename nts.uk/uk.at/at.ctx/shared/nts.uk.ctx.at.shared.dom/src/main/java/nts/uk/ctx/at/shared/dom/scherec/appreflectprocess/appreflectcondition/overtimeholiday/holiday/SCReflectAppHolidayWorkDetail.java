package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.holiday;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;

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
