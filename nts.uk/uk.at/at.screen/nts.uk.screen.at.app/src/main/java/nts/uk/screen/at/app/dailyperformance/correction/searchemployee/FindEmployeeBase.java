package nts.uk.screen.at.app.dailyperformance.correction.searchemployee;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface FindEmployeeBase {

	Optional<DPEmployeeSearchData> findInAllEmployee(String employeeId,
			GeneralDate baseDate, String companyId);
}
