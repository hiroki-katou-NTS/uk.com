/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

/**
 * The Interface PredetemineTimeSetRepository.
 */
public interface PredetemineTimeSetRepository {
	
	/**
	 * Find by code.
	 *
	 * @param companyID the company ID
	 * @param siftCD the sift CD
	 * @return the predetemine time set
	 */
	public PredetemineTimeSet findByCode(String companyID, String siftCD);
}
