package nts.uk.ctx.at.shared.dom.entranceexit;

import java.util.Optional;

/**
 * The Interface ManageEntryExitRepository.
 *
 * @author hoangdd
 */
public interface ManageEntryExitRepository {
	
	/**
	 * Find by ID.
	 *
	 * @param companyID the company ID
	 * @return the optional
	 */
	Optional<ManageEntryExit> findByID(String companyID);
	
	/**
	 * Adds the.
	 *
	 * @param manageEntryExit the manage entry exit
	 */
	void add(ManageEntryExit manageEntryExit);
	
	/**
	 * Update.
	 *
	 * @param manageEntryExit the manage entry exit
	 */
	void update(ManageEntryExit manageEntryExit);
}

