/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentRegularWorkHourFinder.
 */
public class EmpRegularWorkHourFinder {

	/** The empl regular work hour repo. */
	//@Inject
	//private EmpRegularWorkHourRepository empRegularWorkHourRepo;

	/**
	 * Find.
	 *
	 * @param employmentCode
	 *            the employment code
	 * @return the employment regular work hour dto
	 */
	public EmpRegularWorkHourDto find(String employmentCode) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//Optional<EmpRegularWorkHour> empReg = this.empRegularWorkHourRepo.findById(companyId, employmentCode);

		EmpRegularWorkHourDto dto = new EmpRegularWorkHourDto();

		// to data object
//		if (empReg.isPresent()) {
//			empReg.get().saveToMemento(dto);
//		}
		return dto;

	}

}
