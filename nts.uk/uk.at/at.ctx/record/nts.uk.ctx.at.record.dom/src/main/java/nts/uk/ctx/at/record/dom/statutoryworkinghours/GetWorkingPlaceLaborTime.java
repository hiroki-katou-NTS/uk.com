package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public interface GetWorkingPlaceLaborTime {

	Optional<WorkingTimeSetting> getWkpWorkingTimeSetting(String companyId,
			 String employeeId,
			 GeneralDate baseDate,
			 WorkingSystem workingSystem);
	
}
