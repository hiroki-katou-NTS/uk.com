package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication.vacation;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication.CheckConditionReflectAppTimeLeave;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;

/**
 * @author thanh_nx
 *
 *         時間消化休暇の内訳を反映
 */
public class ReflectBreakdownTimeDigestLeave {

	public static DailyRecordOfApplication process(TimeDigestApplicationShare timeDigestApp,
			DailyRecordOfApplication dailyApp, TimeLeaveAppReflectCondition timeLeavCond) {

		// 時間休暇の申請反映条件チェック
		TimeDigestApplicationShare timeDigestCheck = CheckConditionReflectAppTimeLeave.check(timeDigestApp,
				timeLeavCond);

		// 時間消化休暇の内訳を反映
		ReflectTimeDigestLeaveApplication.process(timeDigestCheck, dailyApp, timeLeavCond);
		return dailyApp;

	}
}
