package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.application.furiapp.AbsenceLeaveAppShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.休暇系申請.振休申請
 * 振休申請の反映
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubstituteLeaveAppReflect {
	
	/**
	 * 会社ID
	 */
	private String cid;
	
	/**
	 * 勤務情報、出退勤を反映する
	 */
	private VacationAppReflectOption workInfoAttendanceReflect;

	/**
	 * @author thanh_nx
	 *
	 *         振休申請の反映（勤務実績）
	 */

	public DailyAfterAppReflectResult process(RequireRC require, AbsenceLeaveAppShare absenceLeavApp,
			DailyRecordOfApplication dailyApp) {

		// 休暇系申請の反映
		return this.getWorkInfoAttendanceReflect().process(require, cid, absenceLeavApp.getWorkInformation(),
				absenceLeavApp.getWorkingHours(), absenceLeavApp.getPrePostAtr(), absenceLeavApp.getWorkChangeUse(),
				dailyApp);
	}

	public static interface RequireRC extends VacationAppReflectOption.Require {

	}

	/**
	 * @author thanh_nx
	 *
	 *         振休申請の反映（勤務予定）
	 */

	public DailyAfterAppReflectResult processSC(RequireSC require, AbsenceLeaveAppShare absenceLeavApp,
			DailyRecordOfApplication dailyApp) {

		// 休暇系申請の反映（勤務予定）
		DailyAfterAppReflectResult dailyAppResult = this.getWorkInfoAttendanceReflect().processSC(require, cid, 
				absenceLeavApp.getWorkInformation(), absenceLeavApp.getWorkingHours(),
				absenceLeavApp.getWorkChangeUse(), dailyApp);
		return dailyAppResult;

	}

	public static interface RequireSC extends VacationAppReflectOption.RequireSC {

	}
}
