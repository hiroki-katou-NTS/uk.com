package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataService;

@Stateless
public class DeleteSubstitutionOfHDManaDataCommandHandler extends CommandHandler<DeleteSubstitutionOfHDManaDataCommand> {
   
	@Inject
	private SubstitutionOfHDManaDataRepository SHDMDRepo;
	
	private SubstitutionOfHDManaDataService substitutionOfHDManaDataService;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteSubstitutionOfHDManaDataCommand> context) {
		DeleteSubstitutionOfHDManaDataCommand command = context.getCommand();
	    substitutionOfHDManaDataService.deleteSubsitutionOfHDManaData(command.expirationDate, command.getSID(), command.getDayOff());
	}

}
