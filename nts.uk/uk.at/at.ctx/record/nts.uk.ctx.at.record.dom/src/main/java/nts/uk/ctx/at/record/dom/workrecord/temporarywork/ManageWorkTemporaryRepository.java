/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.temporarywork;

import java.util.Optional;

/**
 * The Interface ManageWorkTemporaryRepository.
 *
 * @author hoangdd
 */
public interface ManageWorkTemporaryRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyID the company ID
	 */
	Optional<ManageWorkTemporary> findByCID(String companyID);
	
	/**
	 * Update.
	 *
	 * @param manageWorkTemporary the manage work temporary
	 */
	void update(ManageWorkTemporary manageWorkTemporary);
	
	/**
	 * Adds the.
	 *
	 * @param manageWorkTemporary the manage work temporary
	 */
	void add(ManageWorkTemporary manageWorkTemporary);
}

