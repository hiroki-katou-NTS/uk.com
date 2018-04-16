package nts.uk.ctx.at.record.app.find.workrecord.monthcal.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComMonthCalSetFinder {

	/** The com defor labor month act cal set repo. */
	@Inject
	private ComDeforLaborMonthActCalSetRepository comDeforLaborMonthActCalSetRepo;

	/** The com flex month act cal set repo. */
	@Inject
	private ComFlexMonthActCalSetRepository comFlexMonthActCalSetRepo;

	/** The com regula month act cal set repo. */
	@Inject
	private ComRegulaMonthActCalSetRepository comRegulaMonthActCalSetRepo;

	/**
	 * get All Work record extracing condition by errorAlarmCheckID
	 */
	public ComMonthCalSetDto getDetails() {
		String cid = AppContexts.user().companyId();

		ComMonthCalSetDto dto = ComMonthCalSetDto.builder().build();

		comDeforLaborMonthActCalSetRepo.find(cid).ifPresent(domain -> domain.saveToMemento(dto));
		comFlexMonthActCalSetRepo.find(cid).ifPresent(domain -> domain.saveToMemento(dto));
		comRegulaMonthActCalSetRepo.find(cid).ifPresent(domain -> domain.saveToMemento(dto));

		return dto;
	}

}
