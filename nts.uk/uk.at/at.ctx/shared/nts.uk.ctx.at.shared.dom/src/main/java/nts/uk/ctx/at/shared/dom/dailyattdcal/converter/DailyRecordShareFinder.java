package nts.uk.ctx.at.shared.dom.dailyattdcal.converter;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

public interface DailyRecordShareFinder {
	
	public Optional<IntegrationOfDaily> find(String employeeId, GeneralDate date);

}
