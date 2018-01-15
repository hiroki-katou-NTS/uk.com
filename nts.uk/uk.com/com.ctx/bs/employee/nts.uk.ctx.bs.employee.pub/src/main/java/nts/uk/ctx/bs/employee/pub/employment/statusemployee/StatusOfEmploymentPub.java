package nts.uk.ctx.bs.employee.pub.employment.statusemployee;

import nts.arc.time.GeneralDate;

public interface StatusOfEmploymentPub {

	/**
	 * Find Status Of Employee by employeeId,referenceDate
	 * For request No.75
	 *
	 */
	StatusOfEmploymentExport getStatusOfEmployment(String employeeId, GeneralDate referenceDate);
}
