/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyRegularLaborHourFinder.
 */
@Stateless
public class ComRegularLaborHourFinder {

	/** The com reg labor hour repo. */
	//@Inject
	//private CompanyRegularLaborHourRepository comRegLaborHourRepo;

	/**
	 * Find.
	 *
	 * @return the company regular labor hour dto
	 */
	public ComRegularLaborHourDto find() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

//		Optional<ComRegularLaborHour> comReg = this.comRegLaborHourRepo.findByCompanyId(companyId);
//
		ComRegularLaborHourDto dto = new ComRegularLaborHourDto();
//
//		// to data object
//		if (comReg.isPresent()) {
//			comReg.get().saveToMemento(dto);
//		}

		return dto;

	}

}
