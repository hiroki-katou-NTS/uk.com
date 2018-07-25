package nts.uk.ctx.at.shared.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;

@Stateless
public class DeletePayoutManagementDataCommandHandler extends CommandHandler<DeletePayoutManagementDataCommand> {

	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;


	@Override
	protected void handle(CommandHandlerContext<DeletePayoutManagementDataCommand> context) {
		DeletePayoutManagementDataCommand command = context.getCommand();
		payoutManagementDataRepository.delete(command.getPayoutId());
	}

}
