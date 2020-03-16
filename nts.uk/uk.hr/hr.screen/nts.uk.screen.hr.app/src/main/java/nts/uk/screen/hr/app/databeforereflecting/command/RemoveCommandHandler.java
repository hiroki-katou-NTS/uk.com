package nts.uk.screen.hr.app.databeforereflecting.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.service.RetirementInformationService;

@Stateless
public class RemoveCommandHandler {

	@Inject
	private RetirementInformationService retirementInformationService;
	
	public void remove(String hisId) {
		
		retirementInformationService.removeRetireInformation(hisId);
	}
	
}
