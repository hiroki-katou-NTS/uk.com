/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ClosureRepository.
 */
public interface ClosureRepository {

	/**
	 * Adds the.
	 *
	 * @param closure the closure
	 */
	void add(Closure closure);

	/**
	 * Update.
	 *
	 * @param closure the closure
	 */
	void update(Closure closure);

	/**
	 * Gets the all closure.
	 *
	 * @param companyId the company id
	 * @return the all closure
	 */
	List<Closure> findAll(String companyId);
	
	/**
	 * Find all use.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<Closure> findAllUse(String companyId);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the optional
	 */
	Optional<Closure> findById(String companyId, int closureId);
	
	/**
	 * Find by list id.
	 *
	 * @param companyId the company id
	 * @param closureIds the closure ids
	 * @return the list
	 */
	List<Closure> findByListId(String companyId, List<Integer> closureIds);

}
