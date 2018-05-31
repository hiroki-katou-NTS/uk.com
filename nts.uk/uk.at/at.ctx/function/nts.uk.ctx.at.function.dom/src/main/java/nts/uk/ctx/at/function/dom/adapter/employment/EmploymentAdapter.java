package nts.uk.ctx.at.function.dom.adapter.employment;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface EmploymentAdapter {
	String getClosure (String employeeId, GeneralDate baseDate);
	/**
	 * Find by date.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId, GeneralDate baseDate);
}
