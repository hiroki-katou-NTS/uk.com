package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public interface GetWorkingPlaceMonthlyLaborTime {
	
	List<MonthlyUnit> getWkpWorkingTimeSetting(String companyId,String employeeId,GeneralDate baseDate,YearMonth yearMonth,WorkingSystem workingSystem);

	
}
