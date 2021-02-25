/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WkpMonthCalSetFinder.
 */
@Stateless
public class WkpMonthCalSetFinder {

	/** The emp defor labor month act cal set repo. */
	@Inject
	private WkpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo;

	/** The emp flex month act cal set repo. */
	@Inject
	private WkpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;

	/** The emp regula month act cal set repo. */
	@Inject
	private WkpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo;

	/**
	 * Gets the details.
	 *
	 * @param wkpId the wkpId
	 * @return the details
	 */
	public WkpMonthCalSetDto getDetails(String wkpId) {
		String cid = AppContexts.user().companyId();

		WkpMonthCalSetDto dto = WkpMonthCalSetDto.builder().build();

		empDeforLaborMonthActCalSetRepo.find(cid, wkpId).ifPresent(domain -> dto.transfer(domain));
		empFlexMonthActCalSetRepo.find(cid, wkpId).ifPresent(domain -> dto.transfer(domain));
		empRegulaMonthActCalSetRepo.find(cid, wkpId).ifPresent(domain -> dto.transfer(domain));

		return dto;
	}

}
