package nts.uk.ctx.at.shared.dom.employmentrules.worktime;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * @author ThanhNX
 *
 *
 *         勤務区分をチェックする
 */
public class CheckWorkDivision {

	public static WorkDivision check(WorkTimeSetting wTimeSetting) {

		WorkTimeDivision div = wTimeSetting.getWorkTimeDivision();
		if (div.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK
				&& div.getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {
			return WorkDivision.FIEXED;
		}

		if (div.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK
				&& div.getWorkTimeMethodSet() == WorkTimeMethodSet.DIFFTIME_WORK) {
			return WorkDivision.NOT_SYSTEM;
		}

		if (div.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK
				&& div.getWorkTimeMethodSet() == WorkTimeMethodSet.FLOW_WORK) {
			return WorkDivision.SYSTEM;
		}

		if (div.getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
			return WorkDivision.FLEX;
		}
		return WorkDivision.FLEX;
	}

}
