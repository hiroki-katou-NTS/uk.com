/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import java.util.List;

/**
 * The Interface ClosureRepository.
 */
public interface ClosureRepository {
	
	/**
	 * Adds the.
	 *
	 * @param closure the closure
	 */
	public void add(Closure closure);
	
	
	/**
	 * Update.
	 *
	 * @param closure the closure
	 */
	public void update(Closure closure);
	
	
	/**
	 * Gets the all closure.
	 *
	 * @param companyId the company id
	 * @return the all closure
	 */
	public List<Closure> getAllClosure(String companyId);

}
