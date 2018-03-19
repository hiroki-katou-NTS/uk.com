/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComCalMonthlyFlex;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyCalMonthlyFlexFinder.
 */
@Stateless
public class CompanyCalMonthlyFlexFinder {

	/** The com cal monthly flex repo. */
	//@Inject
	//private CompanyCalMonthlyFlexRepository comCalMonthlyFlexRepo;

	/**
	 * Find.
	 *
	 * @return the company cal monthly flex dto
	 */
	public CompanyCalMonthlyFlexDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		Optional<ComCalMonthlyFlex> comReg = this.comCalMonthlyFlexRepo.getCompanyCalMonthlyFlexByCid(companyId);

		CompanyCalMonthlyFlexDto dto = new CompanyCalMonthlyFlexDto();

		// to data object
		if (comReg.isPresent()) {
			comReg.get().saveToMemento(dto);
		}

		return dto;
	}
}
