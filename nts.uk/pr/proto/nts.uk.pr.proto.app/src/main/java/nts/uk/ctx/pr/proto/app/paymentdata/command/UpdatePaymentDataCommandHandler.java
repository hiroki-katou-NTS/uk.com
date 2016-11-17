package nts.uk.ctx.pr.proto.app.paymentdata.command;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * Command Handler: add payment data.
 * @author chinhbv
 *
 */
@RequestScoped
@Transactional
public class UpdatePaymentDataCommandHandler  extends CommandHandler<CreatePaymentDataCommand>{

	@Override
	protected void handle(CommandHandlerContext<CreatePaymentDataCommand> arg0) {
		// TODO Auto-generated method stub
		
	}

}
