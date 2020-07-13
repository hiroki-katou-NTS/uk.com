package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm;

import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

public interface ICorrectionAttendanceRule {

	public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt);

}
