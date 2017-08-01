/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

/**
 * The Interface RestTimeRepository.
 */
public interface RestTimeRepository {

	/**
	 * Gets the resttime.
	 *
	 * @param companyId the company id
	 * @param selectedSiftCode the selected sift code
	 * @return the resttime
	 */
	FixedWorkSetting getResttime(String companyId, String selectedSiftCode);
	
}