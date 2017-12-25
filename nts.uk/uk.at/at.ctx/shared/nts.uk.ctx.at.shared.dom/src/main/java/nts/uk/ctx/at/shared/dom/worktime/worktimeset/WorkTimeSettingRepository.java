/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;

/**
 * The Interface WorkTimeSettingRepository.
 */
public interface WorkTimeSettingRepository {

	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<WorkTimeSetting> findByCompanyId(String companyId);

	/**
	 * Find by codes.
	 *
	 * @param companyId the company id
	 * @param codes the codes
	 * @return the list
	 */
	public List<WorkTimeSetting> findByCodes(String companyId, List<String> codes);

	/**
	 * Find by code.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the optional
	 */
	public Optional<WorkTimeSetting> findByCode(String companyId, String workTimeCode);

	
	/**
	 * Find by work atr.
	 *
	 * @param companyId the company id
	 * @param atr the atr
	 * @return the list
	 */
	public List<WorkTimeSetting> findByWorkAtr(String companyId, WorkTimeDailyAtr atr);
	
	/**
	 * Find by work method.
	 *
	 * @param companyId the company id
	 * @param method the method
	 * @return the list
	 */
	public List<WorkTimeSetting> findByWorkMethod(String companyId, WorkTimeMethodSet method);
	
	/**
	 * Find by abolish atr.
	 *
	 * @param companyId the company id
	 * @param atr the atr
	 * @return the list
	 */
	public List<WorkTimeSetting> findByAbolishAtr(String companyId, AbolishAtr atr);
	
	/**
	 * Insert.
	 *
	 * @param domain the domain
	 */
	public void insert(WorkTimeSetting domain);

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	public void update(WorkTimeSetting domain);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	public void remove(String companyId,String workTimeCode);
}
