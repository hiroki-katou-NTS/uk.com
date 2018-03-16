/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeRegularWorkHourFinder.
 */
public class ShainRegularWorkHourFinder {

	/** The emp reg work hour repo. */
	//@Inject
	//private ShainRegularWorkHourRepository empRegWorkHourRepo;

	/**
	 * Find.
	 *
	 * @return the employee regular work hour dto
	 */
	public ShainRegularWorkHourDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get employee id
		String employeeId = loginUserContext.employeeId();

//		Optional<ShainRegularWorkHour> empReg = this.empRegWorkHourRepo
//				.findEmpRegWorkHourByCidAndEmployeeId(companyId, employeeId);
//
		ShainRegularWorkHourDto dto = new ShainRegularWorkHourDto();
//
//		// to data object
//		if (empReg.isPresent()) {
//			empReg.get().saveToMemento(dto);
//		}

		return dto;

	}

}
