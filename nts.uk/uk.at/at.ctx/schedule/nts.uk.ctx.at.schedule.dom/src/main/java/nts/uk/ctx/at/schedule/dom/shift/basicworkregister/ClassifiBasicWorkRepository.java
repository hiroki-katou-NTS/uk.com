/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ClassifiBasicWorkRepository.
 */
public interface ClassifiBasicWorkRepository {
	
	/**
	 * Insert.
	 *
	 * @param classificationBasicWork the classification basic work
	 */
	void insert(ClassificationBasicWork classificationBasicWork);
	
	/**
	 * Update.
	 *
	 * @param classificationBasicWork the classification basic work
	 */
	void update(ClassificationBasicWork classificationBasicWork);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param classificationCode the classification code
	 */
	void remove(String companyId, String classificationCode);	
	

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param classificationCode the classification code
	 * @return the optional
	 */
	Optional<ClassificationBasicWork> findAll(String companyId, String classificationCode);
	
	/**
	 * Find setting.
	 *
	 * @param companyId the company id
	 * @param classificationCode the classification code
	 * @return the list
	 */
	List<ClassificationCode> findSetting(String companyId);
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param classificationCode the classification code
	 * @param workdayAtr the workday atr
	 * @return the optional
	 */
	Optional<ClassificationBasicWork> findById(String companyId, String classificationCode, int workdayAtr);
}
