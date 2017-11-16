package nts.uk.ctx.bs.employee.dom.employeeinfo;

public interface JobEntryHistoryRepository {
	/**
	 * addJobEntryHistory
	 * @param domain
	 */
	void addJobEntryHistory(JobEntryHistory domain);
	
	/**
	 * updateJobEntryHistory
	 * @param domain
	 */
	void updateJobEntryHistory(JobEntryHistory domain);
}
