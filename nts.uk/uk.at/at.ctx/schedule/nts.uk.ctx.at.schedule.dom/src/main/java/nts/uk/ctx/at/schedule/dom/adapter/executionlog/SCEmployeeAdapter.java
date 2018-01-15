package nts.uk.ctx.at.schedule.dom.adapter.executionlog;

import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;

/**
 * The Interface SCEmployeeAdapter.
 */
public interface SCEmployeeAdapter {

	/**
	 * Find by employee id.
	 *
	 * @param sId the s id
	 * @return the employee dto
	 */
	EmployeeDto findByEmployeeId(String sId);
}
