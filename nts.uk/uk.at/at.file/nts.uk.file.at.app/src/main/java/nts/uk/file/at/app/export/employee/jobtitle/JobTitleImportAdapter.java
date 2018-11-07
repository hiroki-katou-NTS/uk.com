package nts.uk.file.at.app.export.employee.jobtitle;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Class JobTitleImportAdapter.
 * @author HoangNDH
 */
public interface JobTitleImportAdapter {
	
	/**
	 * Find by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<EmployeeJobHistExport> findBySid(String employeeId, GeneralDate baseDate);
	/**
	 * Find by ids.
	 *
	 * @param companyId the company id
	 * @param jobIds the job ids
	 * @param baseDate the base date
	 * @return the list
	 */
	List<SimpleJobTitleExport> findByIds(String companyId, List<String> jobIds, GeneralDate baseDate);
}
