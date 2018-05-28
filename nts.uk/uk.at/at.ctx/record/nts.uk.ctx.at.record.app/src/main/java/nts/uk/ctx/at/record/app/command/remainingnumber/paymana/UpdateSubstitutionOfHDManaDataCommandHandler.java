package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataService;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateSubstitutionOfHDManaDataCommandHandler
		extends CommandHandler<UpdateSubstitutionOfHDManaDataCommand> {
	@Inject
	private SubstitutionOfHDManaDataService substitutionOfHDManaDataService;

	@Override
	protected void handle(CommandHandlerContext<UpdateSubstitutionOfHDManaDataCommand> context) {
		UpdateSubstitutionOfHDManaDataCommand command = context.getCommand();
		String cID = AppContexts.user().companyId();

		SubstitutionOfHDManagementData data = new SubstitutionOfHDManagementData(command.getSubOfHDID(), cID,
				command.getEmployeeId(), false, command.getDayoffDate(), command.getRequiredDays(),
				command.getRemainDays());
		substitutionOfHDManaDataService.updateSubOfHD(data, command.getClosureId());
	}

}
