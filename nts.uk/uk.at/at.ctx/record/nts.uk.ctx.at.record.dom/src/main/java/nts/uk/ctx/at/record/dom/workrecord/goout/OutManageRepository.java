/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.goout;

import java.util.Optional;

/**
 * The Interface OutManageRepository.
 *
 * @author Hoangdd
 */
public interface OutManageRepository {
	
	/**
	 * Find by ID.
	 *
	 * @param companyID the company ID
	 * @return the optional
	 */
	Optional<OutManage> findByID(String companyID);
	
	/**
	 * Update.
	 *
	 * @param outManage the out manage
	 */
	void update(OutManage outManage);
	
	/**
	 * Adds the.
	 *
	 * @param outManage the out manage
	 */
	void add(OutManage outManage);
}

