/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmpMonthCalSetFinder.
 */
@Stateless
public class EmpMonthCalSetFinder {

	/** The emp defor labor month act cal set repo. */
	@Inject
	private EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo;

	/** The emp flex month act cal set repo. */
	@Inject
	private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;

	/** The emp regula month act cal set repo. */
	@Inject
	private EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo;

	/**
	 * Gets the details.
	 *
	 * @param emplCode the emplCode
	 * @return the details
	 */
	public EmpMonthCalSetDto getDetails(String emplCode) {
		String cid = AppContexts.user().companyId();

		EmpMonthCalSetDto dto = EmpMonthCalSetDto.builder().build();

		empDeforLaborMonthActCalSetRepo.find(cid, emplCode).ifPresent(domain -> dto.transfer(domain));
		empFlexMonthActCalSetRepo.find(cid, emplCode).ifPresent(domain -> dto.transfer(domain));
		empRegulaMonthActCalSetRepo.find(cid, emplCode).ifPresent(domain -> dto.transfer(domain));

		return dto;
	}

}
