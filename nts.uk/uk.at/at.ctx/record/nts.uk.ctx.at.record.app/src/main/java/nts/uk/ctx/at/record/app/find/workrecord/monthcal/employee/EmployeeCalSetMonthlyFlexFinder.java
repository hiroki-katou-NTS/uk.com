/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShainCalSetMonthlyFlex;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeCalSetMonthlyFlexFinder.
 */
@Stateless
public class EmployeeCalSetMonthlyFlexFinder {

	/** The emp cal set monthly flex repo. */
	//@Inject
	//private EmployeeCalSetMonthlyFlexRepository empCalSetMonthlyFlexRepo;

	/**
	 * Find.
	 *
	 * @return the employee cal set monthly flex dto
	 */
	public EmployeeCalSetMonthlyFlexDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		String employeeId = loginUserContext.employeeId();

		Optional<ShainCalSetMonthlyFlex> empFlex = this.empCalSetMonthlyFlexRepo
				.findEmployeeCalSetMonthlyFlexByCidAndEmpId(companyId, employeeId);

		EmployeeCalSetMonthlyFlexDto dto = new EmployeeCalSetMonthlyFlexDto();

		// to data object
		if (empFlex.isPresent()) {
			empFlex.get().saveToMemento(dto);
		}

		return dto;

	}
}
