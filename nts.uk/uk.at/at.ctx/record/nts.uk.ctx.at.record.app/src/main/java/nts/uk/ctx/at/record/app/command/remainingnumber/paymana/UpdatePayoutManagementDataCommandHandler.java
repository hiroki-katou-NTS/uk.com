package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataService;

@Stateless
public class UpdatePayoutManagementDataCommandHandler extends CommandHandler<PayoutManagementDataCommand> {

	@Inject
	private PayoutManagementDataService payoutMNDRSer;

	@Override
	protected void handle(CommandHandlerContext<PayoutManagementDataCommand> context) {
		PayoutManagementDataCommand command = context.getCommand();
		PayoutManagementData data = new PayoutManagementData(command.getPayoutId(), command.getCID(), command.getSID(),
				command.isUnknownDate(), command.getDayoffDate(), command.getExpiredDate(), command.getLawAtr(),
				command.getOccurredDays(), command.getUnUsedDays(), command.getStateAtr());
		payoutMNDRSer.update(data, command.isCheckBox(), command.getLawAtr(), command.getExpiredDate(),
				command.getUnUsedDays());
	}

}
