/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.WkpCalSetMonthlyActualFlex;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WkpCalSetMonthlyActualFlexFinder.
 */
@Stateless
public class WkpCalSetMonthlyActualFlexFinder {

	/** The wkp cal set monthly actual flex repo. */
	//@Inject
	//private WkpCalSetMonthlyActualFlexRepository wkpCalSetMonthlyActualFlexRepo;

	/**
	 * Find.
	 *
	 * @param wkpId
	 *            the wkp id
	 * @return the wkp cal set monthly actual flex dto
	 */
	public WkpCalSetMonthlyActualFlexDto find(String wkpId) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		Optional<WkpCalSetMonthlyActualFlex> wkpFlex = this.wkpCalSetMonthlyActualFlexRepo.findByCidAndWkpId(companyId,
				wkpId);

		WkpCalSetMonthlyActualFlexDto dto = new WkpCalSetMonthlyActualFlexDto();

		// to data object
		if (wkpFlex.isPresent()) {
			wkpFlex.get().saveToMemento(dto);
		}
		return dto;
	}

}
