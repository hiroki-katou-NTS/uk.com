package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ShaMonthCalSetFinder {

	/** The sha defor labor month act cal set repo. */
	@Inject
	private ShaDeforLaborMonthActCalSetRepository shaDeforLaborMonthActCalSetRepo;

	/** The sha flex month act cal set repo. */
	@Inject
	private ShaFlexMonthActCalSetRepository shaFlexMonthActCalSetRepo;

	/** The sha regula month act cal set repo. */
	@Inject
	private ShaRegulaMonthActCalSetRepository shaRegulaMonthActCalSetRepo;

	/**
	 * get All Work record extracing condition by errorAlarmCheckID
	 */
	public ShaMonthCalSetDto getDetails(String sId) {
		String cid = AppContexts.user().companyId();

		ShaMonthCalSetDto dto = ShaMonthCalSetDto.builder().build();

		shaDeforLaborMonthActCalSetRepo.find(cid, sId).ifPresent(domain -> domain.saveToMemento(dto));
		shaFlexMonthActCalSetRepo.find(cid, sId).ifPresent(domain -> domain.saveToMemento(dto));
		shaRegulaMonthActCalSetRepo.find(cid, sId).ifPresent(domain -> domain.saveToMemento(dto));

		return dto;
	}

}
