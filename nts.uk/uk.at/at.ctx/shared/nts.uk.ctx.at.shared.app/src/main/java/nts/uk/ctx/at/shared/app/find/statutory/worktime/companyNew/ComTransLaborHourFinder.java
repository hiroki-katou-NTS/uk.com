/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyTransLaborHourFinder.
 */
@Stateless
public class ComTransLaborHourFinder {

	/** The com trans labor hour repo. */
	//@Inject
	//private CompanyTransLaborHourRepository comTransLaborHourRepo;

	/**
	 * Find.
	 *
	 * @return the company trans labor hour dto
	 */
	public ComTransLaborHourDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//Optional<CompanyTransLaborHour> comReg = this.comTransLaborHourRepo.getCompanyTransLaborHourByCid(companyId);

		ComTransLaborHourDto dto = new ComTransLaborHourDto();

		// to data object
		//if (comReg.isPresent()) {
		//	comReg.get().saveToMemento(dto);
		//}

		return dto;
	}

}
