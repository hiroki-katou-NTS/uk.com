/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.company;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyLaborRegSetMonthlyActualFinder.
 */
@Stateless
public class CompanyLaborRegSetMonthlyActualFinder {

	/** The com labor reg set monthly actual repo. */
	//@Inject
	//private CompanyLaborRegSetMonthlyActualRepository comLaborRegSetMonthlyActualRepo;

	/**
	 * Find.
	 *
	 * @return the company labor reg set monthly actual dto
	 */
	public CompanyLaborRegSetMonthlyActualDto find() {
		
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

//		Optional<CompanyLaborRegSetMonthlyActual> comReg = this.comLaborRegSetMonthlyActualRepo
//				.getCompanyLaborRegSetMonthlyActualByCid(companyId);
//
		CompanyLaborRegSetMonthlyActualDto dto = new CompanyLaborRegSetMonthlyActualDto();
//
//		// to data object
//		if (comReg.isPresent()) {
//			comReg.get().saveToMemento(dto);
//		}

		return dto;
	}
}
