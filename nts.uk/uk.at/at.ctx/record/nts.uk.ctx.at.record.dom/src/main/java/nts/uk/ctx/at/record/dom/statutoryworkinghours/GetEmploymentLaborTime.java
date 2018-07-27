package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public interface GetEmploymentLaborTime {
	
	Optional<WorkingTimeSetting> getEmpWorkingTimeSetting(String companyId,
			 String employmentCode,
			 WorkingSystem workingSystem);
	
	
}
