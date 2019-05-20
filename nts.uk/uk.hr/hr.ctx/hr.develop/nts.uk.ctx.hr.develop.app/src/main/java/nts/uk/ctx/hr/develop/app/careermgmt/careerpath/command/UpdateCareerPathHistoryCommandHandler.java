package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpathhistory.CareerPathHistService;

@Stateless
public class UpdateCareerPathHistoryCommandHandler {

	@Inject
	private CareerPathHistService service;
	
	public void update(String HisId, GeneralDate startDate) {
		service.updateCareerPathHist(HisId, startDate);
	}
	
}
