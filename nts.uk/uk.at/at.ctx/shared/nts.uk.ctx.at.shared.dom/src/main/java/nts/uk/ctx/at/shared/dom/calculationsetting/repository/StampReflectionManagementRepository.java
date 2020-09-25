package nts.uk.ctx.at.shared.dom.calculationsetting.repository;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;

/**
 * The Interface StampReflectionManagementRepository.
 */
public interface StampReflectionManagementRepository {
	
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<StampReflectionManagement> findByCid(String companyId);

	/**
	 * Update.
	 *
	 * @param reflectionManagement the reflection management
	 */
	void update(StampReflectionManagement reflectionManagement);

	/**
	 * Adds the.
	 *
	 * @param reflectionManagement the reflection management
	 */
	void add(StampReflectionManagement reflectionManagement);
}
