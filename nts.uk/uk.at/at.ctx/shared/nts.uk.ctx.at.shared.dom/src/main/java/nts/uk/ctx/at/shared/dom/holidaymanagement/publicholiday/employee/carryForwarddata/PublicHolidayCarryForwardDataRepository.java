package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata;

import java.util.Optional;


public interface PublicHolidayCarryForwardDataRepository {

	/**
	 * Find by CID.
	 *
	 * @param employeeId the employeeId
	 * @return the optional
	 */
	Optional<PublicHolidayCarryForwardData> get(String employeeId);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(PublicHolidayCarryForwardData domain);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void insert(PublicHolidayCarryForwardData domain);
}
