/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.Optional;

// TODO: Auto-generated Javadoc
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
}
