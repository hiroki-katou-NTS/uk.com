/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime_old;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author Doan Duy Hung
 *
 */

public interface WorkTimeRepository {

	/**
	 * Find by company ID.
	 *
	 * @param companyID the company ID
	 * @return the list
	 */
	public List<WorkTime> findByCompanyID(String companyID);

	/**
	 * Find all.
	 *
	 * @param companyID the company ID
	 * @return the list
	 */
	public List<WorkTime> findAll(String companyID);

	/**
	 * Find by codes.
	 *
	 * @param companyID the company ID
	 * @param codes the codes
	 * @return the list
	 */
	public List<WorkTime> findByCodes(String companyID, List<String> codes);

	/**
	 * get Work Time by Work Time Code and Company ID
	 * 
	 * @param companyID
	 *            Company ID
	 * @param workTimeCD
	 *            Work Time Code
	 * @return Work Time
	 */
	public Optional<WorkTime> findByCode(String companyID, String siftCD);

	/**
	 * get list Work Time by Work Time Code list and Company ID
	 * 
	 * @param companyID
	 *            Company ID
	 * @param workTimeCDs
	 *            Work Time Code list
	 * @return list Work Time
	 */
	public List<WorkTime> findByCodeList(String companyID, List<String> siftCDs);
}
