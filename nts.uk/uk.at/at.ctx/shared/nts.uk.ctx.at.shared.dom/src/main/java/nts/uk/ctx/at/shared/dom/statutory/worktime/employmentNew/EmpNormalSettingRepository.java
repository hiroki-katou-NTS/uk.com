/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmpMentNormalSettingRepository.
 */
public interface EmpNormalSettingRepository {

	/**
	 * Find by cid and empl code and year.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @param year the year
	 * @return the optional
	 */
	Optional<EmpNormalSetting> find(String cid, String emplCode, int year);

	/**
	 * Adds the.
	 *
	 * @param empNormalSetting the emp ment normal setting
	 */
	void add(EmpNormalSetting empNormalSetting);

	/**
	 * Update.
	 *
	 * @param empNormalSetting the emp ment normal setting
	 */
	void update(EmpNormalSetting empNormalSetting);

	/**
	 * Delete.
	 *
	 * @param empNormalSetting the emp ment normal setting
	 */
	void delete(String cid, String emplCode, int year);
	
	/**
	 * Find list.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @return the list
	 */
	List<EmpNormalSetting> findList(String cid, String emplCode);
}
