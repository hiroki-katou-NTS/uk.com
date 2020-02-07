package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpathhistory.CareerPathHistService;

@Stateless
public class AddCareerPathHistoryCommandHandler{

	@Inject
	private CareerPathHistService service; 
	
	public String add(GeneralDate startDate) {
		return service.addCareerPathHist(startDate);
	}

}
