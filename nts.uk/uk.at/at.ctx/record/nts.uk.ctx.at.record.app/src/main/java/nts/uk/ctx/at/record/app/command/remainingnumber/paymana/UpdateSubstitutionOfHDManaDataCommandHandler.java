package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;


import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataService;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

@Stateless
public class UpdateSubstitutionOfHDManaDataCommandHandler
		extends CommandHandler<UpdateSubstitutionOfHDManaDataCommand> {

	@Inject
	private SubstitutionOfHDManaDataRepository subHDMDTRepo;
	
	@Inject
	private SubstitutionOfHDManaDataService substitutionOfHDManaDataService;

	@Override
	protected void handle(CommandHandlerContext<UpdateSubstitutionOfHDManaDataCommand> context) {
		UpdateSubstitutionOfHDManaDataCommand command = context.getCommand();
		SubstitutionOfHDManagementData data = new SubstitutionOfHDManagementData(command.getSubOfHDID(),
				command.getCid(), command.getSID(), command.getHolidayDate(), command.getRequiredDays(),
				command.getRemainDays());
		substitutionOfHDManaDataService.updateSub(data, command.getExprirationDate());
	}

}
