/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.company;

import java.util.Optional;

/**
 * The Class CompanyEstablishmentRepository.
 */
public interface CompanyEstablishmentRepository {

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the optional
	 */
	public Optional<CompanyEstablishment> findById(String companyId, int targetYear);

	public Optional<CompanyEstablishment> findById2(String companyId, int targetYear);
	
	
	/**
	 * Save company establishment.
	 *
	 * @param companyEstablishment the company establishment
	 */
	public void saveCompanyEstablishment(CompanyEstablishment companyEstablishment);
	
	
	
	/**
	 * Removes the company establishment.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 */
	public void removeCompanyEstablishment(String companyId, int targetYear);
	
	
	/**
	 * Check setting.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return true, if successful
	 */
	public boolean checkSetting(String companyId, int targetYear);
}
