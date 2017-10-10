package nts.uk.ctx.bs.employee.dom.jobtitle.info;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.HistoryId;

/**
 * The Interface JobTitleInfoGetMemento.
 */
public interface JobTitleInfoGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	

	/**
	 * Gets the list job title history id.
	 *
	 * @return the list job title history id
	 */
	public HistoryId getJobTitleHistoryId();
	
	/**
	 * Gets the job title id.
	 *
	 * @return the job title id
	 */
	public JobTitleId getJobTitleId();
	
	/**
	 * Gets the job title code.
	 *
	 * @return the job title code
	 */
	public JobTitleCode getJobTitleCode();
	
	/**
	 * Gets the job title name.
	 *
	 * @return the job title name
	 */
	public JobTitleName getJobTitleName();
	
	/**
	 * Gets the sequence code.
	 *
	 * @return the sequence code
	 */
	public SequenceCode getSequenceCode();	
	
}
