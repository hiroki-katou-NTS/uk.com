package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePayoutManagementDataCommandHandler extends CommandHandler<PayoutManagementDataCommand> {

	@Inject
	private PayoutManagementDataService payoutManagementDataService;

	@Override
	protected void handle(CommandHandlerContext<PayoutManagementDataCommand> context) {
		PayoutManagementDataCommand command = context.getCommand();
		String cID = AppContexts.user().companyId();
		PayoutManagementData data = new PayoutManagementData(command.getPayoutId(), cID, command.getEmployeeId(),
				command.isUnknownDate(), command.getDayoffDate(), command.getExpiredDate(), command.getLawAtr(),
				command.getOccurredDays(), command.getUnUsedDays(), command.getStateAtr());
		payoutManagementDataService.update(data, command.getClosureId(), command.isCheckBox());
	}

}
