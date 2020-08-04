package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public interface DailyStatutoryWorkingHours {

	DailyUnit getDailyUnit(String companyId,
			  String employmentCd,
			  String employeeId,
			  GeneralDate baseDate,
			  WorkingSystem workingSystem);
	
	DailyUnit getDailyUnit(String companyId,
			  String employmentCd,
			  String employeeId,
			  GeneralDate baseDate,
			  WorkingSystem workingSystem,
			  Optional<UsageUnitSetting> usageSetting);
	
	
	Optional<WorkingTimeSetting> getWorkingTimeSetting(String companyId,
			  String employmentCd,
			  String employeeId,
			  GeneralDate baseDate,
			  WorkingSystem workingSystem);
	
}
