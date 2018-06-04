package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SysWorkplaceAdapter {
	/**
	 * Find by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	// RequestList30
	Optional<SWkpHistImport> findBySid (String employeeId, GeneralDate baseDate);
}
