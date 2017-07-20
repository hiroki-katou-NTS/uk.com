/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

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
	 * Find.
	 *
	 * @param companyId the company id
	 * @param classificationCode the classification code
	 * @return the optional
	 */
	Optional<ClassificationBasicWork> find(String companyId, String classificationCode);
	

//	List<ClassificationBasicWork> findAll(String CompanyId, String classificationCode);
}
