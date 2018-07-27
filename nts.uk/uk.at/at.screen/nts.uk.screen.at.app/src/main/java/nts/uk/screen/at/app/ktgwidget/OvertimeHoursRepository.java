package nts.uk.screen.at.app.ktgwidget;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface OvertimeHoursRepository {

	List<String> getAffWkpHistItemByListWkpIdAndDatePeriod(DatePeriod basedate, List<String> workplaceId);
	List<String> getListEmptByListCodeAndDatePeriod(DatePeriod datePeriod, List<String> employmentCodes );
	List<AffCompanyHist> getAffComHisEmpByLstSidAndPeriod(List<String> employeeIds, DatePeriod datePeriod);
}
