package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;

public class DeleteSubstitutionOfHDManaDataCommandHandler extends CommandHandler<DeletePayoutManagementDataCommand> {
   
	@Inject
	private SubstitutionOfHDManaDataRepository SHDMDRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeletePayoutManagementDataCommand> context) {
		DeletePayoutManagementDataCommand command = context.getCommand();
	    SHDMDRepo.delete(command.getSid());
		
	}

}
