package nts.uk.ctx.at.shared.dom.workmanagementmultiple;

import java.util.Optional;


/**
 * The Interface WorkManagementMultipleRepository.
 */
public interface WorkManagementMultipleRepository {


	/**
	 * Find by code.
	 *
	 * @param companyID the company ID
	 * @return the optional
	 */
	public Optional<WorkManagementMultiple> findByCode(String companyID);
	
	/**
	 * Insert domain.
	 *
	 * @param setting the setting
	 */
	public void insert (WorkManagementMultiple setting);
	
	/**
	 * Update domain.
	 *
	 * @param setting the setting
	 */
	public void update (WorkManagementMultiple setting);
}
