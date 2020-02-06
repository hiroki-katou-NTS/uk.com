package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface OptionalWidgetAdapter {

	/**
	 * Execute the algorithm "01. Display overtime indicator count"
	 * @return
	 */
	int getNumberOT(String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	
	/**
	 * Execute the algorithm "02. Display of break indication number of items"
	 * @return
	 */
	int getNumberBreakIndication(String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	/**get request list 365*/
	Optional<OptionalWidgetImport> getSelectedWidget(String companyId, String topPagePartCode);
	
	/**get request list 305*/
	List<EmployeeErrorImport> checkEmployeeErrorOnProcessingDate(String employeeId, DatePeriod datePeriod);
	
	/**get request list 236*/
	List<ApplicationTimeImport> acquireTotalApplicationOverTimeHours(String sId, GeneralDate startDate, GeneralDate endDate);
	
	/**get request list 193*/
	List<DailyExcessTotalTimeImport> getExcessTotalTime(String employeeId, DatePeriod datePeriod);
	
	/**get request list 298*/
	List<ApplicationTimeImport> acquireTotalApplicationTimeUnreflected(String sId, GeneralDate startDate, GeneralDate endDate);
	
	/**get request list 299*/
	List<ApplicationTimeImport> acquireTotalAppHdTimeNotReflected(String sId, GeneralDate startDate, GeneralDate endDate);
	
	/**get request list 300*/
	List<ApplicationTimeImport> acquireAppNotReflected(String sId, GeneralDate startDate, GeneralDate endDate);
	
	/**get request list 210*/
	List<NextAnnualLeaveGrantImport> acquireNextHolidayGrantDate(String cId, String employeeId, GeneralDate endDate);
	
	/**get request list 198*/
	NumAnnLeaReferenceDateImport getReferDateAnnualLeaveRemainNumber(String employeeID,GeneralDate date);
	
	/**get request list 446*/
	List<DailyLateAndLeaveEarlyTimeImport> engravingCancelLateorLeaveearly(String employeeID, GeneralDate startDate, GeneralDate endDate);
	
	/**get request list 197*/
	List<DailyLateAndLeaveEarlyTimeImport> getLateLeaveEarly(String employeeId, DatePeriod datePeriod);
	
	/**get request list 201*/
	KTGRsvLeaveInfoImport getNumberOfReservedYearsRemain(String employeeId, GeneralDate date);
	
	/**get request list 467
	 * @return 0(労働日数-WORKING_DAY), 1(出勤率-ATTENDENCE_RATE), 3(The annual holiday grant table setting is not done.)*/
	int getGrantHdTblSet(String companyId, String employeeId); 
}
