package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
public class PayoutSubofHDManagementCommandHandler extends CommandHandler<PayoutSubofHDManagementCommand> {

	@Override
	protected void handle(CommandHandlerContext<PayoutSubofHDManagementCommand> context) {
		val command = context.getCommand();
		
	}

}
 