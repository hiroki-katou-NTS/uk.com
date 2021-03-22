package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.holiday;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

/**
 * @author thanh_nx
 *
 *         休日出勤申請の反映（勤務実績）
 */
public class RCReflectAppHolidayWorkDetail {

	public static DailyAfterAppReflectResult process(Require require,  String cid, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp, HdWorkAppReflect holidayWorkAppReflect) {
		List<Integer> lstId = new ArrayList<Integer>();
		// [input. 休日出勤申請. 事前事後区分]をチェック
		if (holidayApp.getPrePostAtr() == PrePostAtrShare.PREDICT) {

			// 事前休日出勤申請の反映
			lstId.addAll(RCBeforeReflectAppHolidayWork
					.process(require, holidayApp, dailyApp, holidayWorkAppReflect.getBefore()).getLstItemId());
		} else {

			// 事後休日出勤申請の反映
			lstId.addAll(RCAfterReflectAppHolidayWork
					.process(require, cid, holidayApp, dailyApp, holidayWorkAppReflect.getAfter()).getLstItemId());
		}
		
		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	public static interface Require
			extends RCBeforeReflectAppHolidayWork.Require, RCAfterReflectAppHolidayWork.Require {

	}
}
