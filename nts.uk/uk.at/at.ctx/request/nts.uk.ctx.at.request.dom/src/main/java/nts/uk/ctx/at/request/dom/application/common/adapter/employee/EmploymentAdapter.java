package nts.uk.ctx.at.request.dom.application.common.adapter.employee;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;

public interface EmploymentAdapter {

	Optional<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId, GeneralDate baseDate);
	
}
