package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpathhistory.CareerPathHistService;

@Stateless
public class RemoveCareerPathHistoryCommandHandler {

	@Inject
	private CareerPathHistService service;
	
	public void remove(String HisId) {
		service.removeCareerPathHist(HisId);
	}
}
