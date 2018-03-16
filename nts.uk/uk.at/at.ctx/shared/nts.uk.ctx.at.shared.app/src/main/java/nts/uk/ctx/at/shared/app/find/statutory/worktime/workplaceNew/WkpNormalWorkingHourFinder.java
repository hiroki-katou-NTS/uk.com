/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WkpNormalWorkingHourFinder.
 */
@Stateless
public class WkpNormalWorkingHourFinder {

	/** The wkp normal working hour repo. */
	//@Inject
	//private WkpNormalWorkingHourRepository wkpNormalWorkingHourRepo;

	/**
	 * Find.
	 *
	 * @param wkpId
	 *            the wkp id
	 * @return the wkp normal working hour dto
	 */
	public WkpNormalWorkingHourDto find(String wkpId) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//Optional<WkpNormalWorkingHour> wkpReg = this.wkpNormalWorkingHourRepo.findById(companyId, wkpId);

		WkpNormalWorkingHourDto dto = new WkpNormalWorkingHourDto();

		// to data object
		//if (wkpReg.isPresent()) {
		//	wkpReg.get().saveToMemento(dto);
		//}
		return dto;

	}

}
