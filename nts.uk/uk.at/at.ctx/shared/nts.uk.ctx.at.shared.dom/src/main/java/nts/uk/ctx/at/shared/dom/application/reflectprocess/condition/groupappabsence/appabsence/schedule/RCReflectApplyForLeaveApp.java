package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.groupappabsence.appabsence.schedule;

import lombok.val;
import nts.uk.ctx.at.shared.dom.application.appabsence.ApplyForLeaveShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.groupappabsence.algorithm.RCReflectGroupApplyForLeaveApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication.vacation.ReflectBreakdownTimeDigestLeave;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;

/**
 * @author thanh_nx
 *
 *         休暇申請の反映（勤務実績）
 */
public class RCReflectApplyForLeaveApp {

	public static DailyAfterAppReflectResult process(Require require, ApplyForLeaveShare leavApp,
			DailyRecordOfApplication dailyApp, VacationApplicationReflect reflectAppSet) {

		// 休暇系申請の反映
		val groupAppLeav = RCReflectGroupApplyForLeaveApp.process(require,
				leavApp.getReflectFreeTimeApp().getWorkInfo(), leavApp.getReflectFreeTimeApp().getWorkingHours(),
				leavApp.getPrePostAtr(), leavApp.getReflectFreeTimeApp().getWorkChangeUse(), dailyApp,
				reflectAppSet.getWorkAttendanceReflect());

		// 時間消化休暇の内訳を反映
		leavApp.getReflectFreeTimeApp().getTimeDegestion().ifPresent(x -> {
			groupAppLeav.setDomainDaily(
					ReflectBreakdownTimeDigestLeave.process(x, dailyApp, reflectAppSet.getTimeLeaveReflect()));
		});

		return groupAppLeav;
	}

	public static interface Require extends RCReflectGroupApplyForLeaveApp.Require {

	}
}
