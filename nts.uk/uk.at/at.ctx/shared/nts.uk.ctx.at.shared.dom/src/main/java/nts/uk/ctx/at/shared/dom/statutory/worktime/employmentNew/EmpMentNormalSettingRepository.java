/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface EmpMentNormalSettingRepository.
 */
public interface EmpMentNormalSettingRepository {

	/**
	 * Find by cid and empl code and year.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @param year the year
	 * @return the optional
	 */
	Optional<EmpMentNormalSetting> findByCidAndEmplCodeAndYear(String cid, String emplCode, Year year);

	/**
	 * Adds the.
	 *
	 * @param empMentNormalSetting the emp ment normal setting
	 */
	void add(EmpMentNormalSetting empMentNormalSetting);

	/**
	 * Update.
	 *
	 * @param empMentNormalSetting the emp ment normal setting
	 */
	void update(EmpMentNormalSetting empMentNormalSetting);

	/**
	 * Delete.
	 *
	 * @param empMentNormalSetting the emp ment normal setting
	 */
	void delete(EmpMentNormalSetting empMentNormalSetting);
}
