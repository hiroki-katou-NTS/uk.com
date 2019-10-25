package nts.uk.ctx.hr.develop.app.databeforereflecting.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.databeforereflecting.service.RetirementInformationService;

@Stateless
public class RemoveCommandHandler {

	@Inject
	private RetirementInformationService retirementInformationService;
	
	public void remove(String hisId) {
		
		retirementInformationService.removeRetireInformation(hisId);
	}
	
}
