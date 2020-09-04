package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.ArrayList;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         [RQ667]申請反映後の日別勤怠(work）を作成する（勤務実績）
 */
public class RCCreateDailyAfterApplicationeReflect {

	// TODO: Can xu ly all
	public static DailyAfterAppReflectResult process(Require require, ApplicationShare application,
			IntegrationOfDaily domainDaily, GeneralDate date) {

		return new DailyAfterAppReflectResult(domainDaily, new ArrayList<>());
	}

	public static interface Require {

	}
}
