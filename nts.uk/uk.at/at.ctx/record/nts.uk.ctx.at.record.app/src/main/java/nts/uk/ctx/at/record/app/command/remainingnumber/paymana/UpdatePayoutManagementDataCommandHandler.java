package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

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
		PayoutManagementData data = new PayoutManagementData(command.getPayoutId(), command.getCID(), command.getSID(),
				command.getPayoutDate(), command.getExpiredDate(), command.getLawAtr(), command.getOccurredDays(),
				command.getUnUsedDays(), command.getStateAtr());
		payoutMNDTRepo.update(data);
	}

}
