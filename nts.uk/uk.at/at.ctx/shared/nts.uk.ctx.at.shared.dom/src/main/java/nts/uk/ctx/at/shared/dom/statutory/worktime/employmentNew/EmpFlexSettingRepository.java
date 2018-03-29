/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.Optional;

/**
 * The Interface EmpMentFlexSettingRepository.
 */
public interface EmpFlexSettingRepository {

	/**
	 * Find by cid and empl code and year.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @param year the year
	 * @return the optional
	 */
	Optional<EmpFlexSetting> find(String cid, String emplCode, int year);

	/**
	 * Adds the.
	 *
	 * @param empMentFlexSetting the emp ment flex setting
	 */
	void add(EmpFlexSetting empMentFlexSetting);

	/**
	 * Update.
	 *
	 * @param empMentFlexSetting the emp ment flex setting
	 */
	void update(EmpFlexSetting empMentFlexSetting);

	/**
	 * Delete.
	 *
	 * @param empMentFlexSetting the emp ment flex setting
	 */
	void delete(String cid, String emplCode, int year);

}
