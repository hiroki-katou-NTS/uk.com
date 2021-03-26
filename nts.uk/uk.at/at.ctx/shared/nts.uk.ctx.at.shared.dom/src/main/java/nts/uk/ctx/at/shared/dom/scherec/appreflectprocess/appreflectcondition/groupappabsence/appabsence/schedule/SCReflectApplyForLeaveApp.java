package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.groupappabsence.appabsence.schedule;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.appabsence.ApplyForLeaveShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.groupappabsence.algorithm.SCReflectGroupApplyForLeaveApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.vacation.ReflectBreakdownTimeDigestLeave;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;

/**
 * @author thanh_nx
 *
 *         休暇申請の反映（勤務予定）
 */
public class SCReflectApplyForLeaveApp {

	public static DailyAfterAppReflectResult process(Require require, ApplyForLeaveShare leavApp,
			DailyRecordOfApplication dailyApp, VacationApplicationReflect reflectAppSet) {

		// 休暇系申請の反映（勤務予定）
		val groupAppLeav = SCReflectGroupApplyForLeaveApp.process(require,
				leavApp.getReflectFreeTimeApp().getWorkInfo(), leavApp.getReflectFreeTimeApp().getWorkingHours(),
				leavApp.getReflectFreeTimeApp().getWorkChangeUse(), dailyApp, reflectAppSet.getWorkAttendanceReflect());

		// 時間消化休暇の内訳を反映
		leavApp.getReflectFreeTimeApp().getTimeDegestion().ifPresent(x -> {
			groupAppLeav.setDomainDaily(
					ReflectBreakdownTimeDigestLeave.process(x, dailyApp, reflectAppSet.getTimeLeaveReflect()));
		});

		return groupAppLeav;
	}

	public static interface Require extends SCReflectGroupApplyForLeaveApp.Require {

	}
}
