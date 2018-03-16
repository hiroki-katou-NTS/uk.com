/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment.EmpRegularSetMonthlyActualWork;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentRegularSetMonthlyActualWorkFinder.
 */
@Stateless
public class EmploymentRegularSetMonthlyActualWorkFinder {

	/** The empl reg set monthly actual work repo. */
	//@Inject
	//private EmploymentRegularSetMonthlyActualWorkRepository emplRegSetMonthlyActualWorkRepo;

	/**
	 * Find.
	 *
	 * @param emplCode
	 *            the empl code
	 * @return the employment regular set monthly actual work dto
	 */
	public EmploymentRegularSetMonthlyActualWorkDto find(String emplCode) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		Optional<EmpRegularSetMonthlyActualWork> emplDefor = this.emplRegSetMonthlyActualWorkRepo
				.findByCidAndEmplCode(companyId, emplCode);

		EmploymentRegularSetMonthlyActualWorkDto dto = new EmploymentRegularSetMonthlyActualWorkDto();

		// to data object
		if (emplDefor.isPresent()) {
			emplDefor.get().saveToMemento(dto);
		}
		return dto;

	}

}
