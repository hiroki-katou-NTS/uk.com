/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.Optional;

/**
 * The Interface EmpMentDeforLaborSettingRepository.
 */
public interface EmpDeforLaborSettingRepository {

	/**
	 * Find by cid and empl code and year.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @param year the year
	 * @return the optional
	 */
	Optional<EmpDeforLaborSetting> find(String cid, String emplCode, int year);

	/**
	 * Adds the.
	 *
	 * @param empMentDeforLaborSetting the emp ment defor labor setting
	 */
	void add(EmpDeforLaborSetting empMentDeforLaborSetting);

	/**
	 * Update.
	 *
	 * @param empMentDeforLaborSetting the emp ment defor labor setting
	 */
	void update(EmpDeforLaborSetting empMentDeforLaborSetting);

	/**
	 * Delete.
	 *
	 * @param empMentDeforLaborSetting the emp ment defor labor setting
	 */
	void delete(String cid, String emplCode, int year);
}
