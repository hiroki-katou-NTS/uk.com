/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.workplace;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WkpTransLaborSetMonthlyFinder.
 */
@Stateless
public class WkpTransLaborSetMonthlyFinder {

	/** The wkp trans labor set monthly repo. */
	//@Inject
	//private WkpTransLaborSetMonthlyRepository wkpTransLaborSetMonthlyRepo;

	/**
	 * Find.
	 *
	 * @param wkpId
	 *            the wkp id
	 * @return the wkp trans labor set monthly dto
	 */
	public WkpTransLaborSetMonthlyDto find(String wkpId) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

//		Optional<WkpTransLaborSetMonthly> wkpTrans = this.wkpTransLaborSetMonthlyRepo.findByCidAndWkpid(companyId,
//				wkpId);
//
		WkpTransLaborSetMonthlyDto dto = new WkpTransLaborSetMonthlyDto();
//
//		// to data object
//		if (wkpTrans.isPresent()) {
//			wkpTrans.get().saveToMemento(dto);
//		}
		return dto;
	}
}
