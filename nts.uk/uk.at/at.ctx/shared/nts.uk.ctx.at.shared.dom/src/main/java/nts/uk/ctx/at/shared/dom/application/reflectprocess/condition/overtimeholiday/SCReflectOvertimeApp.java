package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday;

import java.util.List;

import nts.uk.ctx.at.shared.dom.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.overtime.schedule.SCReflectOvertimeAppliction;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;

/**
 * @author thanh_nx
 *
 *         残業申請を反映する（勤務予定）
 */
public class SCReflectOvertimeApp {

	public static List<Integer> process(Require require, AppOverTimeShare overTimeApp,
			DailyRecordOfApplication dailyApp, AppReflectOtHdWork reflectOvertimeSet) {

		return SCReflectOvertimeAppliction.process(require, overTimeApp, dailyApp,
				reflectOvertimeSet.getOvertimeWorkAppReflect());
	}

	public static interface Require extends SCReflectOvertimeAppliction.Require {

	}
}
