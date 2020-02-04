package nts.uk.screen.at.app.ktgwidget;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.arc.time.calendar.period.DatePeriod;

public interface OvertimeHoursRepository {

	List<String> getAffWkpHistItemByListWkpIdAndDatePeriod(DatePeriod basedate, List<String> workplaceId);
	List<String> getListEmptByListCodeAndDatePeriod(DatePeriod datePeriod, List<String> employmentCodes );
	List<AffCompanyHist> getAffComHisEmpByLstSidAndPeriod(List<String> employeeIds, DatePeriod datePeriod);
}
