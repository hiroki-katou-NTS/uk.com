/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmpMonthCalSetFinder.
 */
@Stateless
public class EmpMonthCalSetFinder {

	/** The emp defor labor month act cal set repo. */
	@Inject
	private EmpDeforLaborMonthActCalSetRepository empDeforLaborMonthActCalSetRepo;

	/** The emp flex month act cal set repo. */
	@Inject
	private EmpFlexMonthActCalSetRepository empFlexMonthActCalSetRepo;

	/** The emp regula month act cal set repo. */
	@Inject
	private EmpRegulaMonthActCalSetRepository empRegulaMonthActCalSetRepo;

	/**
	 * Gets the details.
	 *
	 * @param emplCode the emplCode
	 * @return the details
	 */
	public EmpMonthCalSetDto getDetails(String emplCode) {
		String cid = AppContexts.user().companyId();

		EmpMonthCalSetDto dto = EmpMonthCalSetDto.builder().build();

		empDeforLaborMonthActCalSetRepo.find(cid, emplCode).ifPresent(domain -> domain.saveToMemento(dto));
		empFlexMonthActCalSetRepo.find(cid, emplCode).ifPresent(domain -> domain.saveToMemento(dto));
		empRegulaMonthActCalSetRepo.find(cid, emplCode).ifPresent(domain -> domain.saveToMemento(dto));

		return dto;
	}

}
