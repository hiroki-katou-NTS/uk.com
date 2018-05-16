package nts.uk.ctx.at.record.app.command.remainingnumber.paymana1;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataRepository;

@Stateless
public class UpdatePayoutManagementDataCommandHandler extends CommandHandler<PayoutManagementDataCommand> {

	
	@Inject
	private PayoutManagementDataRepository payoutMNDTRepo;
	
	@Override
	protected void handle(CommandHandlerContext<PayoutManagementDataCommand> context) {
		PayoutManagementDataCommand command = context.getCommand();
		Optional<PayoutManagementData> managementData = payoutMNDTRepo.findByID(command.getSID());
		PayoutManagementData data = managementData.get();
		payoutMNDTRepo.update(data);
	}

}
