package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.schedule;

import java.util.List;

import nts.uk.ctx.at.shared.dom.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;

/**
 * @author thanh_nx
 *
 *         残業申請の反映（勤務予定）
 */
public class SCReflectOvertimeAppliction {

	public static List<Integer> process(Require require, AppOverTimeShare overTimeApp,
			DailyRecordOfApplication dailyApp, OtWorkAppReflect reflectOvertimeSet) {

		return SCReflectBeforeOvertimeApp.process(require, overTimeApp, dailyApp, reflectOvertimeSet.getBefore());

	}

	public static interface Require extends SCReflectBeforeOvertimeApp.Require {

	}
}
