package nts.uk.ctx.bs.employee.dom.jobtitle.info;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleId;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.HistoryId;

/**
 * The Interface JobTitleInfoSetMemento.
 */
public interface JobTitleInfoSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the job title history id.
	 *
	 * @param jobTitleHistoryId the new job title history id
	 */
	public void setJobTitleHistoryId(HistoryId jobTitleHistoryId);
	
	/**
	 * Sets the job title id.
	 *
	 * @param jobTitleId the new job title id
	 */
	public void setJobTitleId(JobTitleId jobTitleId);
	
	/**
	 * Sets the job title code.
	 *
	 * @param jobTitleCode the new job title code
	 */
	public void setJobTitleCode(JobTitleCode jobTitleCode);
	
	/**
	 * Sets the job title name.
	 *
	 * @param jobTitleName the new job title name
	 */
	public void setJobTitleName(JobTitleName jobTitleName);
	
	/**
	 * Sets the sequence code.
	 *
	 * @param sequenceCode the new sequence code
	 */
	public void setSequenceCode(SequenceCode sequenceCode);
	
}
