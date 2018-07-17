/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;

/**
 * The Interface DiffTimeWorkSettingRepository.
 */
public interface DiffTimeWorkSettingRepository {

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the optional
	 */
	Optional<DiffTimeWorkSetting> find(String companyId, String workTimeCode);

	/**
	 * Adds the.
	 *
	 * @param diffTimeWorkSetting the diff time work setting
	 */
	void add(DiffTimeWorkSetting diffTimeWorkSetting);

	/**
	 * Update.
	 *
	 * @param diffTimeWorkSetting the diff time work setting
	 */
	void update(DiffTimeWorkSetting diffTimeWorkSetting);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	void remove(String companyId, String workTimeCode);
	
	/**
	 * Find by C id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<DiffTimeWorkSetting> findByCId(String companyId);

	/**
	 * Gets the diff offday work rest timezones.
	 *
	 * @param companyId the company id
	 * @return the diff offday work rest timezones
	 */
	Map<WorkTimeCode, List<AmPmWorkTimezone>> getDiffOffdayWorkRestTimezones(String companyId, List<String> workTimeCodes);

	/**
	 * Gets the diff half day work rest timezones.
	 *
	 * @param companyId the company id
	 * @return the diff half day work rest timezones
	 */
	Map<WorkTimeCode, List<AmPmWorkTimezone>> getDiffHalfDayWorkRestTimezones(String companyId, List<String> workTimeCodes);
}
