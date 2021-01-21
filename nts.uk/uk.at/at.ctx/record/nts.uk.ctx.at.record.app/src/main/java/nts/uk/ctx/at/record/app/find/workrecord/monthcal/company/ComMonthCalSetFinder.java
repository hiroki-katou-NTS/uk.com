package nts.uk.ctx.at.record.app.find.workrecord.monthcal.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComMonthCalSetFinder {

	/** The com defor labor month act cal set repo. */
	@Inject
	private ComDeforLaborMonthActCalSetRepo comDeforLaborMonthActCalSetRepo;

	/** The com flex month act cal set repo. */
	@Inject
	private ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo;

	/** The com regula month act cal set repo. */
	@Inject
	private ComRegulaMonthActCalSetRepo comRegulaMonthActCalSetRepo;

	/**
	 * get All Work record extracing condition by errorAlarmCheckID
	 */
	public ComMonthCalSetDto getDetails() {
		String cid = AppContexts.user().companyId();

		ComMonthCalSetDto dto = ComMonthCalSetDto.builder().build();

		comDeforLaborMonthActCalSetRepo.find(cid).ifPresent(domain -> dto.transfer(domain));
		comFlexMonthActCalSetRepo.find(cid).ifPresent(domain -> dto.transfer(domain));
		comRegulaMonthActCalSetRepo.find(cid).ifPresent(domain -> dto.transfer(domain));

		return dto;
	}

}
