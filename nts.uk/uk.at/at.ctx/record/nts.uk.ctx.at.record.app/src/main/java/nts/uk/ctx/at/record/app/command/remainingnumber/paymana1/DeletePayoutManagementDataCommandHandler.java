package nts.uk.ctx.at.record.app.command.remainingnumber.paymana1;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataRepository;

public class DeletePayoutManagementDataCommandHandler extends CommandHandler<DeletePayoutManagementDataCommand> {
    
	
	@Inject
	private PayoutManagementDataRepository payoutMNDTRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeletePayoutManagementDataCommand> context) {
		DeletePayoutManagementDataCommand command = context.getCommand();
		payoutMNDTRepo.delete(command.getSid());
	}

}
