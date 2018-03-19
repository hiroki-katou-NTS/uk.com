/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentCalMonthlyFlexFinder.
 */
@Stateless
public class EmploymentCalMonthlyFlexFinder {

	/** The empl cal monthly flex repo. */
	//@Inject
	//private EmploymentCalMonthlyFlexRepository emplCalMonthlyFlexRepo;

	/**
	 * Find.
	 *
	 * @param employmentCode
	 *            the employment code
	 * @return the employment cal monthly flex dto
	 */
	public EmploymentCalMonthlyFlexDto find(String employmentCode) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

//		Optional<EmploymentCalMonthlyFlex> emplFlex = this.emplCalMonthlyFlexRepo.findByCidAndEmplCode(companyId,
//				employmentCode);

		EmploymentCalMonthlyFlexDto dto = new EmploymentCalMonthlyFlexDto();

		// to data object
//		if (emplFlex.isPresent()) {
//			emplFlex.get().saveToMemento(dto);
//		}
		return dto;
	}

}
