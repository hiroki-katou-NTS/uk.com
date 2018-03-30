/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WkpMonthCalSetFinder.
 */
@Stateless
public class WkpMonthCalSetFinder {

	/** The emp defor labor month act cal set repo. */
	@Inject
	private WkpDeforLaborMonthActCalSetRepository empDeforLaborMonthActCalSetRepo;

	/** The emp flex month act cal set repo. */
	@Inject
	private WkpFlexMonthActCalSetRepository empFlexMonthActCalSetRepo;

	/** The emp regula month act cal set repo. */
	@Inject
	private WkpRegulaMonthActCalSetRepository empRegulaMonthActCalSetRepo;

	/**
	 * Gets the details.
	 *
	 * @param wkpId the wkpId
	 * @return the details
	 */
	public WkpMonthCalSetDto getDetails(String wkpId) {
		String cid = AppContexts.user().companyId();

		WkpMonthCalSetDto dto = WkpMonthCalSetDto.builder().build();

		empDeforLaborMonthActCalSetRepo.find(cid, wkpId).ifPresent(domain -> domain.saveToMemento(dto));
		empFlexMonthActCalSetRepo.find(cid, wkpId).ifPresent(domain -> domain.saveToMemento(dto));
		empRegulaMonthActCalSetRepo.find(cid, wkpId).ifPresent(domain -> domain.saveToMemento(dto));

		return dto;
	}

}
