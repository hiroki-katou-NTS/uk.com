package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.record.ReflectOvertimeApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         残業申請を反映する（勤務実績）
 */
public class RCReflectOvertimeApp {

	public static List<Integer> process(Require require, String cid, AppOverTimeShare overTimeApp,
			DailyRecordOfApplication dailyApp, AppReflectOtHdWork reflectOvertimeSet) {

		List<Integer> lstId = new ArrayList<Integer>();
		// 残業申請の反映
		lstId.addAll(ReflectOvertimeApplication.process(require, cid, overTimeApp, dailyApp,
				reflectOvertimeSet.getOvertimeWorkAppReflect()));

		// [時間外深夜時間を反映する]をチェック
		if (reflectOvertimeSet.getNightOvertimeReflectAtr() == NotUseAtr.USE) {
			// 時間外深夜時間の反映
			ReflectLateNightOvertime.process(dailyApp, overTimeApp.getApplicationTime(), overTimeApp.getPrePostAtr());
		}

		return lstId;

	}

	public static interface Require extends ReflectOvertimeApplication.Require {

	}
}
