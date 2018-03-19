/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentLaborDeforSetTemporaryFinder.
 */
@Stateless
public class EmploymentLaborDeforSetTemporaryFinder {

	/** The empl labor defor set temp repo. */
	//@Inject
	//private EmploymentLaborDeforSetTemporaryRepository emplLaborDeforSetTempRepo;

	/**
	 * Find.
	 *
	 * @param employeeCode
	 *            the employee code
	 * @return the employment labor defor set temporary dto
	 */
	public EmploymentLaborDeforSetTemporaryDto find(String employeeCode) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//Optional<EmploymentLaborDeforSetTemporary> emplDefor = this.emplLaborDeforSetTempRepo.findById(companyId,
		//		employeeCode);

		EmploymentLaborDeforSetTemporaryDto dto = new EmploymentLaborDeforSetTemporaryDto();

		// to data object
		//if (emplDefor.isPresent()) {
		//	emplDefor.get().saveToMemento(dto);
		//}
		return dto;
	}
}
