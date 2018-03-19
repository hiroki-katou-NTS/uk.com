/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShainLaborDeforSetTemporary;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeLaborDeforSetTemporaryFinder.
 */
@Stateless
public class EmployeeLaborDeforSetTemporaryFinder {

	/** The empl labor defor set temp repo. */
	//@Inject
	//private EmployeeLaborDeforSetTemporaryRepository emplLaborDeforSetTempRepo;

	/**
	 * Find.
	 *
	 */
	public EmployeeLaborDeforSetTemporaryDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		String employeeId = loginUserContext.employeeId();

		Optional<ShainLaborDeforSetTemporary> empDefor = this.emplLaborDeforSetTempRepo
				.findEmpLaborDeforSetTempByCidAndEmpId(companyId, employeeId);

		EmployeeLaborDeforSetTemporaryDto dto = new EmployeeLaborDeforSetTemporaryDto();

		// to data object
		if (empDefor.isPresent()) {
			empDefor.get().saveToMemento(dto);
		}

		return dto;

	}
}
