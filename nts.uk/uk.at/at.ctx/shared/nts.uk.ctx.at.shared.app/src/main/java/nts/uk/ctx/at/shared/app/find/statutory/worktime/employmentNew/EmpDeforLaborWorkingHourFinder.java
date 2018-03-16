/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentDeforLaborWorkingHourFinder.
 */
public class EmpDeforLaborWorkingHourFinder {

	/** The empl defor labor working hour repo. */
	//@Inject
	//private EmpDeforLaborWorkingHourRepository empDeforLaborWorkingHourRepo;

	/**
	 * Find.
	 *
	 * @param employmentCode
	 *            the employment code
	 * @return the employment defor labor working hour dto
	 */
	public EmpDeforLaborWorkingHourDto find(String employmentCode) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

//		Optional<EmpDeforLaborWorkingHour> emplDefor = this.empDeforLaborWorkingHourRepo
//				.findByCidAndEmplCode(companyId, employmentCode);

		EmpDeforLaborWorkingHourDto dto = new EmpDeforLaborWorkingHourDto();
//
//		// to data object
//		if (emplDefor.isPresent()) {
//			emplDefor.get().saveToMemento(dto);
//		}
		return dto;

	}

}
