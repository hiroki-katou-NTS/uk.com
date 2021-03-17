package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.otheritem.ReflectOtherItems;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak.ReflectBreakApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer.TranferOvertimeCompensatory;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.AfterOtWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         事後残業申請の反映（勤務実績）
 */
public class RCReflectAfterOvertimeApp {

	public static List<Integer> process(Require require, String cid, AppOverTimeShare overTimeApp,
			DailyRecordOfApplication dailyApp, AfterOtWorkAppReflect reflectOvertimeBeforeSet) {

		List<Integer> lstId = new ArrayList<>();
		// [出退勤を反映する]をチェック
		if (reflectOvertimeBeforeSet.getWorkReflect() == NotUseAtr.USE) {
			// 出退勤の反映
			lstId.addAll(ReflectAttendance.reflect(overTimeApp.getWorkHoursOp(), ScheduleRecordClassifi.RECORD,
					dailyApp, Optional.of(true), Optional.of(true)));
		}

		// 残業時間の反映
		ReflectApplicationTime.process(overTimeApp.getApplicationTime().getApplicationTime(), dailyApp,
				Optional.of(ReflectAppDestination.RECORD));

		// その他項目の反映
		ReflectOtherItems.process(overTimeApp.getApplicationTime(), dailyApp, reflectOvertimeBeforeSet.getOthersReflect());

		// 休憩の申請反映
		ReflectBreakApplication.process(overTimeApp.getBreakTimeOp(), dailyApp,
				reflectOvertimeBeforeSet.getBreakLeaveApplication());

		// 残業時間の代休振替
		TranferOvertimeCompensatory.process(require, cid, dailyApp.getDomain());
		return lstId;
	}

	public static interface Require extends TranferOvertimeCompensatory.Require {

	}
}
