package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import java.util.Optional;

/**
 * The Interface PublicHolidayManagementUsageUnitRepository.
 */
public interface PublicHolidayManagementUsageUnitRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<PublicHolidayManagementUsageUnit> findByCID(String companyId);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(PublicHolidayManagementUsageUnit domain);
}
