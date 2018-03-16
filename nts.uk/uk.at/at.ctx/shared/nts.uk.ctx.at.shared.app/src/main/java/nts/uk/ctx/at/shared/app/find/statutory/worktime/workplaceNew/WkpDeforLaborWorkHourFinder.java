/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WkpDeforLaborWorkHourFinder.
 */
public class WkpDeforLaborWorkHourFinder {

	/** The wkp defor labor work hour repo. */
	//@Inject
	//private WkpDeforLaborWorkHourRepository wkpDeforLaborWorkHourRepo;

	/**
	 * Find.
	 *
	 * @param wkpId
	 *            the wkp id
	 * @return the wkp defor labor work hour dto
	 */
	public WkpDeforLaborWorkHourDto find(String wkpId) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//Optional<WkpDeforLaborWorkHour> wkpDefor = this.wkpDeforLaborWorkHourRepo.findByCidAndWkpId(companyId, wkpId);

		WkpDeforLaborWorkHourDto dto = new WkpDeforLaborWorkHourDto();

		// to data object
		//if (wkpDefor.isPresent()) {
		//	wkpDefor.get().saveToMemento(dto);
		//}
		return dto;

	}

}
