package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public interface GetEmploymentMonthlyLaborTime {
	
	List<MonthlyUnit> getEmpWorkingTimeSetting(String companyId,String employmentCode,YearMonth yearMonth,WorkingSystem workingSystem);

	
}
