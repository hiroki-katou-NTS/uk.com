/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.workplace;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WkpRegularSetMonthlyActualWorkFinder.
 */
@Stateless
public class WkpRegularSetMonthlyActualWorkFinder {

	/** The wkp reg set monthly actual work repo. */
	//@Inject
	//private WkpRegularSetMonthlyActualWorkRepository wkpRegSetMonthlyActualWorkRepo;

	/**
	 * Find.
	 *
	 * @return the wkp regular set monthly actual work
	 */
	public WkpRegularSetMonthlyActualWorkDto find(String wkpId) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

//		Optional<WkpRegularSetMonthlyActualWork> wkpReg = this.wkpRegSetMonthlyActualWorkRepo
//				.findByCidAndWkpId(companyId, wkpId);
//
		WkpRegularSetMonthlyActualWorkDto dto = new WkpRegularSetMonthlyActualWorkDto();
//
//		// to data object
//		if (wkpReg.isPresent()) {
//			wkpReg.get().saveToMemento(dto);
//		}
		return dto;

	}

}
