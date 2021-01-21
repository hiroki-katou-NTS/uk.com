package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ShaMonthCalSetFinder {

	/** The sha defor labor month act cal set repo. */
	@Inject
	private ShaDeforLaborMonthActCalSetRepo shaDeforLaborMonthActCalSetRepo;

	/** The sha flex month act cal set repo. */
	@Inject
	private ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo;

	/** The sha regula month act cal set repo. */
	@Inject
	private ShaRegulaMonthActCalSetRepo shaRegulaMonthActCalSetRepo;

	/**
	 * get All Work record extracing condition by errorAlarmCheckID
	 */
	public ShaMonthCalSetDto getDetails(String sId) {
		String cid = AppContexts.user().companyId();

		ShaMonthCalSetDto dto = ShaMonthCalSetDto.builder().build();

		shaDeforLaborMonthActCalSetRepo.find(cid, sId).ifPresent(domain -> dto.transfer(domain));
		shaFlexMonthActCalSetRepo.find(cid, sId).ifPresent(domain -> dto.transfer(domain));
		shaRegulaMonthActCalSetRepo.find(cid, sId).ifPresent(domain -> dto.transfer(domain));

		return dto;
	}

}
