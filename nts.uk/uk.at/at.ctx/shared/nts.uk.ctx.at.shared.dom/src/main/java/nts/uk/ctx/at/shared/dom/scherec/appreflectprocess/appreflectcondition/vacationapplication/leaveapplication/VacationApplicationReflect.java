package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.application.appabsence.ApplyForLeaveShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;

/**
 * refactor4 refactor 4
 * 休暇申請の反映
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.休暇系申請.休暇申請
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VacationApplicationReflect extends AggregateRoot {

    private String companyId;

    /**
     * 勤務情報、出退勤を反映する
     */
    private VacationAppReflectOption workAttendanceReflect;

    /**
     * 時間休暇を反映する
     */
    private TimeLeaveAppReflectCondition timeLeaveReflect;
    
	/**
	 * @author thanh_nx
	 *
	 *         休暇申請の反映（勤務実績）
	 */
	public DailyAfterAppReflectResult process(Require require, ApplyForLeaveShare leavApp,
			DailyRecordOfApplication dailyApp) {

		// 休暇系申請の反映
		val groupAppLeav = this.getWorkAttendanceReflect().process(require,
				leavApp.getReflectFreeTimeApp().getWorkInfo(), leavApp.getReflectFreeTimeApp().getWorkingHours(),
				leavApp.getPrePostAtr(), leavApp.getReflectFreeTimeApp().getWorkChangeUse(), dailyApp);

		// 時間消化休暇の内訳を反映
		leavApp.getReflectFreeTimeApp().getTimeDegestion().ifPresent(x -> {
			groupAppLeav.setDomainDaily(
					this.getTimeLeaveReflect().process(x, dailyApp));
		});

		return groupAppLeav;
	}

	public static interface Require extends VacationAppReflectOption.Require {

	}
	
	/**
	 * @author thanh_nx
	 *
	 *         休暇申請の反映（勤務予定）
	 */
	public DailyAfterAppReflectResult processSC(RequireSC require, ApplyForLeaveShare leavApp,
			DailyRecordOfApplication dailyApp) {

		// 休暇系申請の反映（勤務予定）
		val groupAppLeav = this.getWorkAttendanceReflect().processSC(require,
				leavApp.getReflectFreeTimeApp().getWorkInfo(), leavApp.getReflectFreeTimeApp().getWorkingHours(),
				leavApp.getReflectFreeTimeApp().getWorkChangeUse(), dailyApp);

		// 時間消化休暇の内訳を反映
		leavApp.getReflectFreeTimeApp().getTimeDegestion().ifPresent(x -> {
			groupAppLeav.setDomainDaily(
					this.getTimeLeaveReflect().process(x, dailyApp));
		});

		return groupAppLeav;
	}

	public static interface RequireSC extends VacationAppReflectOption.RequireSC {

	}
}
