/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkTimeSettingRepository.
 */
public interface WorkTimeSettingRepository {

	/**
	 * Find by company ID.
	 *
	 * @param companyID the company ID
	 * @return the list
	 */
	public List<WorkTimeSetting> findByCompanyID(String companyID);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<WorkTimeSetting> findAll(String companyId);

	/**
	 * Find by codes.
	 *
	 * @param companyID the company ID
	 * @param codes the codes
	 * @return the list
	 */
	public List<WorkTimeSetting> findByCodes(String companyID, List<String> codes);

	/**
	 * Find by code.
	 *
	 * @param companyID the company ID
	 * @param workTimeCode the work time code
	 * @return the work time setting
	 */
	public Optional<WorkTimeSetting> findByCode(String companyID, String workTimeCode);

	/**
	 * Find by code list.
	 *
	 * @param companyID the company ID
	 * @param siftCDs the sift C ds
	 * @return the list
	 */
	public List<WorkTimeSetting> findByCodeList(String companyID, List<String> siftCDs);
}
