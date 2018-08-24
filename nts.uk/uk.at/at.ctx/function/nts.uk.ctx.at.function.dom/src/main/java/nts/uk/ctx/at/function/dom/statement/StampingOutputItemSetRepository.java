/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.statement;

import java.util.List;
import java.util.Optional;

/**
 * The Interface StampingOutputItemSetRepository.
 */
public interface StampingOutputItemSetRepository {
	
	/**
	 * Gets the by cid and code.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the by cid and code
	 */
	public Optional<StampingOutputItemSet> getByCidAndCode(String companyId, String code);
	
	/**
	 * Gets the by cid.
	 *
	 * @param companyId the company id
	 * @return the by cid
	 */
	public List<StampingOutputItemSet> getByCid(String companyId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	public void add(StampingOutputItemSet domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	public void update(StampingOutputItemSet domain);
	
	/**
	 * Removes the by cid and code.
	 *
	 * @param companyId the company id
	 * @param code the code
	 */
	public void removeByCidAndCode(String companyId, String code);
}
