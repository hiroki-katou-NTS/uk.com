/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.company;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyLaborDeforSetMonthlyFinder.
 */
@Stateless
public class CompanyLaborDeforSetMonthlyFinder {

	/** The com labor defor set monthly repo. */
	//@Inject
	//private CompanyLaborDeforSetMonthlyRepository comLaborDeforSetMonthlyRepo;

	/**
	 * Find.
	 *
	 * @return the company labor defor set monthly dto
	 */
	public CompanyLaborDeforSetMonthlyDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

//		Optional<CompanyLaborDeforSetMonthly> comReg = this.comLaborDeforSetMonthlyRepo
//				.getCompanyLaborDeforSetMonthlyByCid(companyId);
//		
		CompanyLaborDeforSetMonthlyDto dto = new CompanyLaborDeforSetMonthlyDto();
//
//		// to data object
//		if (comReg.isPresent()) {
//			comReg.get().saveToMemento(dto);
//		}

		return dto;
	}

}
