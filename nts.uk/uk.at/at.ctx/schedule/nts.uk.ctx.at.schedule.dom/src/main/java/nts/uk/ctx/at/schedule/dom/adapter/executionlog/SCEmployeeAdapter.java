package nts.uk.ctx.at.schedule.dom.adapter.executionlog;

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
