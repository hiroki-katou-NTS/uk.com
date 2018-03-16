/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeSpeDeforLaborHourFinder.
 */
public class ShainSpeDeforLaborHourFinder {

	/** The emp spe defor labor hour repo. */
	//@Inject
	//private ShainSpeDeforLaborHourRepository shainSpeDeforLaborHourRepo;

	/**
	 * Find.
	 *
	 * @return the employee spe defor labor hour dto
	 */
	public ShainSpeDeforLaborHourDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get employee id
		String employeeId = loginUserContext.employeeId();

//		Optional<ShainSpeDeforLaborHour> empReg = this.empSpeDeforLaborHourRepo
//				.findEmployeeSpeDeforLaborByCidAndEmpId(companyId, employeeId);
//
		ShainSpeDeforLaborHourDto dto = new ShainSpeDeforLaborHourDto();
//
//		// to data object
//		if (empReg.isPresent()) {
//			empReg.get().saveToMemento(dto);
//		}
		return dto;
	}
}
