package nts.uk.ctx.at.schedule.dom.adapter.jobtitle;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
public interface SyJobTitleAdapter {
	/**
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	Optional<EmployeeJobHistImported> findBySid(String employeeId, GeneralDate baseDate);
}
