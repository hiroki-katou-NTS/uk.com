package nts.uk.ctx.at.shared.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataService;

@Stateless
@Transactional
public class SubOfHDPayoutManaDataCommandHandler extends CommandHandler<SubOfHDPayoutManaDataCommand>{

	@Inject
	private PayoutManagementDataService payoutManagementDataService;
	
	@Override
	protected void handle(CommandHandlerContext<SubOfHDPayoutManaDataCommand> context) {
		val command = context.getCommand();
		payoutManagementDataService.insertPayoutSubofHD(command.getSid(), command.getSubOfHDID(), command.getRemainNumber(), command.getPayout());
	}

}
