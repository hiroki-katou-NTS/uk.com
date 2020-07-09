package dailyattdcal.dailywork.algorithm;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;

public interface ICorrectionAttendanceRule {

	public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt);

}
