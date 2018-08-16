package nts.uk.ctx.at.shared.app.command.remainingnumber.paymana;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdatePayoutManagementDataCommandHandler extends CommandHandlerWithResult<PayoutManagementDataCommand, List<String>> {

	@Inject
	private PayoutManagementDataService payoutManagementDataService;

	@Override
	protected List<String> handle(CommandHandlerContext<PayoutManagementDataCommand> context) {
		PayoutManagementDataCommand command = context.getCommand();
		String cID = AppContexts.user().companyId();
		PayoutManagementData data = new PayoutManagementData(command.getPayoutId(), cID, command.getEmployeeId(),
				command.isUnknownDate(), command.getDayoffDate(), command.getExpiredDate(), command.getLawAtr(),
				command.getOccurredDays(), command.getUnUsedDays(), command.getStateAtr());
		return payoutManagementDataService.update(data, command.getClosureId(), command.isCheckBox());
	}

}
