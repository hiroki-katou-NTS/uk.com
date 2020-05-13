package nts.uk.screen.hr.app.databeforereflecting.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.service.RetirementInformationService;
import nts.uk.screen.hr.app.databeforereflecting.find.DatabeforereflectingFinder;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveCommandHandler extends CommandHandlerWithResult<String, Boolean>{

	@Inject
	private RetirementInformationService retirementInformationService;

	@Inject
	private DatabeforereflectingFinder finder;

	@Override
	protected Boolean handle(CommandHandlerContext<String> context) {
		
		String histId = context.getCommand();
		
		retirementInformationService.removeRetireInformation(histId);

		boolean checkRetiredEmployeeList = finder.getRetiredEmployeeList(AppContexts.user().companyId());

		if (checkRetiredEmployeeList) {
			return true;

		} else {
			return false;

		}
	}

}
