/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkTypeRepository.
 */
public interface WorkTypeRepository {

	/**
	 * Gets the possible work type.
	 *
	 * @param companyId
	 *            the company id
	 * @param lstPossible
	 *            the lst possible
	 * @return the possible work type
	 */
	List<WorkType> getPossibleWorkType(String companyId, List<String> lstPossible);

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	List<WorkType> findByCompanyId(String companyId);

	/**
	 * Find not deprecated.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	List<WorkType> findNotDeprecated(String companyId);

	/**
	 * Find not deprecated by list code.
	 *
	 * @param companyId
	 *            the company id
	 * @param codes
	 *            the codes
	 * @return the list
	 */
	List<WorkType> findNotDeprecatedByListCode(String companyId, List<String> codes);

	/**
	 * Find by companyId and workTypeCd.
	 *
	 * @param companyId
	 *            the company id
	 * @param workTypeCd
	 *            the work type cd
	 * @return WorkType
	 */
	Optional<WorkType> findByPK(String companyId, String workTypeCd);
	
	/**
	 * 
	 * @param companyId
	 * @param workTypeCd
	 * @return
	 */
	List<WorkTypeSet> findWorkTypeSet(String companyId, String workTypeCode);

	/**
	 * Insert workType to DB
	 * 
	 * @param workType
	 */
	void add(WorkType workType);
	
	/**
	 * Update workType to DB
	 * 
	 * @param workType
	 */
	void update(WorkType workType);
	
	/**
	 * Insert workTypeSet to DB
	 * 
	 * @param workTypeSet
	 */
	void addWorkTypeSet(WorkTypeSet workTypeSet);
	
	/**
	 * Delete workTypeSet to DB
	 * 
	 * @param workTypeSet
	 */
	void removeWorkTypeSet(String companyId, String workTypeCd);
	
	/**
	 * Delete workType to DB
	 * 
	 * @param workTypeCd
	 */
	void remove(String companyId, String workTypeCd);
}
