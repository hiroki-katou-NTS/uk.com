/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle.info;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceCode;

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
	 * Gets the job title history id.
	 *
	 * @return the job title history id
	 */
	public String getJobTitleHistoryId();
	
	/**
	 * Gets the checks if is manager.
	 *
	 * @return the checks if is manager
	 */
	public boolean getIsManager();
	
	/**
	 * Gets the job title id.
	 *
	 * @return the job title id
	 */
	public String getJobTitleId();
	
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
