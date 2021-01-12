package nts.uk.ctx.at.shared.dom.dailyattdcal.converter;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

public interface DailyRecordShareFinder {
	
	public IntegrationOfDaily find(String employeeId, GeneralDate date);

}
