package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public interface MonthlyStatutoryWorkingHours {

	Optional<MonAndWeekStatutoryTime> getMonAndWeekStatutoryTime(String companyId,
			  String employmentCd,
			  String employeeId,
			  GeneralDate baseDate,
			  YearMonth yearMonth,
			  WorkingSystem workingSystem);
	
	MonthlyFlexStatutoryLaborTime getFlexMonAndWeekStatutoryTime(String companyId,
				String employmentCd,
				String employeeId,
				GeneralDate baseDate,
				YearMonth yearMonth
				);

}
