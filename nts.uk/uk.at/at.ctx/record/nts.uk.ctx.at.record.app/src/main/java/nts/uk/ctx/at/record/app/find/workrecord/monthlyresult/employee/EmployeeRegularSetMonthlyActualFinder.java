/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.employee;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeRegularSetMonthlyActualFinder.
 */
@Stateless
public class EmployeeRegularSetMonthlyActualFinder {

	/** The empl reg set monthly actual repo. */
	//@Inject
	//private EmployeeRegularSetMonthlyActualRepository emplRegSetMonthlyActualRepo;

	/**
	 * Find.
	 *
	 * @return the employee regular set monthly actual dto
	 */
	public EmployeeRegularSetMonthlyActualDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		String employeeId = loginUserContext.employeeId();

//		Optional<EmployeeRegularSetMonthlyActual> empReg = this.emplRegSetMonthlyActualRepo
//				.findEmpRegSetMonthlyActualByCidAndEmpId(companyId, employeeId);
//
		EmployeeRegularSetMonthlyActualDto dto = new EmployeeRegularSetMonthlyActualDto();
//
//		// to data object
//		if (empReg.isPresent()) {
//			empReg.get().saveToMemento(dto);
//		}

		return dto;

	}

}
