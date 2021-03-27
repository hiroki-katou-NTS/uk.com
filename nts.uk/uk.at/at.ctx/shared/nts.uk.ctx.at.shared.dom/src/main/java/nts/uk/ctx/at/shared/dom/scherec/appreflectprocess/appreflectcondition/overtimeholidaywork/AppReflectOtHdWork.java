package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.ReflectLateNightHolidayWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.ReflectLateNightOvertime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.残業・休日出勤.残業休日出勤申請の反映
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppReflectOtHdWork extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 休日出勤申請
	 */
	private HdWorkAppReflect holidayWorkAppReflect;

	/**
	 * 残業申請
	 */
	private OtWorkAppReflect overtimeWorkAppReflect;

	/**
	 * 時間外深夜時間を反映する
	 */
	private NotUseAtr nightOvertimeReflectAtr;

	/**
	 * @author thanh_nx
	 *
	 *         残業申請を反映する（勤務実績）
	 */

	public List<Integer> processOverRc(RequireRC require, String cid, AppOverTimeShare overTimeApp,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();
		// 残業申請の反映
		lstId.addAll(this.getOvertimeWorkAppReflect().process(require, cid, overTimeApp, dailyApp));

		// [時間外深夜時間を反映する]をチェック
		if (this.getNightOvertimeReflectAtr() == NotUseAtr.USE) {
			// 時間外深夜時間の反映
			ReflectLateNightOvertime.process(dailyApp, overTimeApp.getApplicationTime(), overTimeApp.getPrePostAtr());
		}

		return lstId;

	}

	public static interface RequireRC extends OtWorkAppReflect.RequireRC {

	}
	
	/**
	 * @author thanh_nx
	 *
	 *         残業申請を反映する（勤務予定）
	 */

	public List<Integer> processRC(RequireSC require, AppOverTimeShare overTimeApp, DailyRecordOfApplication dailyApp) {

		return this.getOvertimeWorkAppReflect().process(require, overTimeApp, dailyApp);
	}

	public static interface RequireSC extends OtWorkAppReflect.RequireSC {

	}
	
	/**
	 * @author thanh_nx
	 *
	 *         休日出勤申請を反映する（勤務予定）
	 */

	public DailyAfterAppReflectResult processHolSc(RequireHolSC require, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp) {

		// 休日出勤申請の反映（勤務予定）
		return this.getHolidayWorkAppReflect().process(require, holidayApp, dailyApp);
	}

	public static interface RequireHolSC extends HdWorkAppReflect.RequireSC {

	}

	/**
	 * @author thanh_nx
	 *
	 *         休日出勤申請を反映する（勤務実績）
	 */
	public DailyAfterAppReflectResult process(Require require, String cid, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstId = new ArrayList<Integer>();
		// 休日出勤申請の反映
		lstId.addAll(
				this.getHolidayWorkAppReflect().process(require, cid, holidayApp, dailyApp).getLstItemId());

		// [時間外深夜時間を反映する]をチェック
		if (this.getNightOvertimeReflectAtr() == NotUseAtr.USE) {
			// 時間外深夜時間の反映
			ReflectLateNightHolidayWork.process(dailyApp, holidayApp.getApplicationTime(), holidayApp.getPrePostAtr());
		}

		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	public static interface Require extends HdWorkAppReflect.Require {

	}

}
