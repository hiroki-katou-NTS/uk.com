/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;

/**
 * The Interface FixedWorkSettingRepository.
 */
public interface FixedWorkSettingRepository {

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(FixedWorkSetting domain);

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(FixedWorkSetting domain);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	void remove(String companyId, String workTimeCode);

	/**
	 * Find by key.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the optional
	 */
	Optional<FixedWorkSetting> findByKey(String companyId, String workTimeCode);

	/**
	 * Find by C id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<FixedWorkSetting> findByCId(String companyId);

	/**
	 * Gets the fix offday work rest timezones.
	 *
	 * @param companyId the company id
	 * @return the fix offday work rest timezones
	 */
	Map<WorkTimeCode, List<AmPmWorkTimezone>> getFixOffdayWorkRestTimezones(String companyId, List<String> workTimeCodes);

	/**
	 * Gets the fix half day work rest timezones.
	 *
	 * @param companyId the company id
	 * @return the fix half day work rest timezones
	 */
	Map<WorkTimeCode, List<AmPmWorkTimezone>> getFixHalfDayWorkRestTimezones(String companyId, List<String> workTimeCodes);
}
