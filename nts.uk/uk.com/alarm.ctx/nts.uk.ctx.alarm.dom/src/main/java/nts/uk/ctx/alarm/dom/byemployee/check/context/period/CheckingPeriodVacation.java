package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * 休暇データのチェック対象期間
 */
public class CheckingPeriodVacation {

	public DatePeriod calculatePeriod(Require require, String employeeId) {
		throw new RuntimeException("not implemented");
	}

	public interface Require {

	}
}
