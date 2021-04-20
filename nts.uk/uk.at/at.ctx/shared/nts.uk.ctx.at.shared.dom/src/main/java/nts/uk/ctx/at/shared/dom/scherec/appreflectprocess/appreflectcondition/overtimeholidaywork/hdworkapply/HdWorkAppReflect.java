package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.残業・休日出勤.休日出勤申請.休日出勤申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HdWorkAppReflect {
	/**
	 * 事前
	 */
	private BeforeHdWorkAppReflect before;

	/**
	 * 事後
	 */
	private AfterHdWorkAppReflect after;

	public static HdWorkAppReflect create(int reflectActualHolidayWorkAtr, int workReflect, int reflectPaytime,
			int reflectDivergence, int reflectBreakOuting) {
		return new HdWorkAppReflect(
				new BeforeHdWorkAppReflect(EnumAdaptor.valueOf(reflectActualHolidayWorkAtr, NotUseAtr.class)),
				AfterHdWorkAppReflect.create(workReflect, reflectPaytime, reflectDivergence, reflectBreakOuting));
	}

	/**
	 * @author thanh_nx
	 *
	 *         休日出勤申請の反映（勤務実績）
	 */

	public DailyAfterAppReflectResult process(Require require, String cid, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstId = new ArrayList<Integer>();
		// [input. 休日出勤申請. 事前事後区分]をチェック
		if (holidayApp.getPrePostAtr() == PrePostAtrShare.PREDICT) {

			// 事前休日出勤申請の反映
			lstId.addAll(this.getBefore().process(require, cid, holidayApp, dailyApp).getLstItemId());
		} else {

			// 事後休日出勤申請の反映
			lstId.addAll(this.getAfter().process(require, cid, holidayApp, dailyApp).getLstItemId());
		}

		return new DailyAfterAppReflectResult(dailyApp, lstId);
	}

	public static interface Require extends BeforeHdWorkAppReflect.Require, AfterHdWorkAppReflect.Require {

	}

	/**
	 * @author thanh_nx
	 *
	 *         休日出勤申請の反映（勤務予定）
	 */

	public DailyAfterAppReflectResult process(RequireSC require, String cid, AppHolidayWorkShare holidayApp,
			DailyRecordOfApplication dailyApp) {

		// 休日出勤申請の反映（勤務予定）
		return this.getBefore().process(require, cid, holidayApp, dailyApp);
	}

	public static interface RequireSC extends BeforeHdWorkAppReflect.Require {

	}
}
