package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpath.CareerPathService;

@Stateless
public class AddCareerPathCommandHandler{

	@Inject
	private CareerPathService service; 
	
	public void add(CareerPartCommand careerPartCommand) {
		if(careerPartCommand.getIsNotice()) {
			//get notice
			
			service.addCareerPath(careerPartCommand.toDomain());
		}else {
			service.addCareerPath(careerPartCommand.toDomain());
		}
	}

}
