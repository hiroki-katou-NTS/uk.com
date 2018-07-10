package nts.uk.ctx.at.shared.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataService;

@Stateless
@Transactional
public class PayoutSubofHDManagementCommandHandler extends CommandHandler<PayoutSubofHDManagementCommand> {

	@Inject
	private SubstitutionOfHDManaDataService SubstitutionOfHDManaDataService;
	
	@Override
	protected void handle(CommandHandlerContext<PayoutSubofHDManagementCommand> context) {
		val command = context.getCommand();
		SubstitutionOfHDManaDataService.insertSubOfHDMan(command.getSid(),command.getPayoutID(),command.getRemainNumber(), command.getSubofHD());
	}

}
 