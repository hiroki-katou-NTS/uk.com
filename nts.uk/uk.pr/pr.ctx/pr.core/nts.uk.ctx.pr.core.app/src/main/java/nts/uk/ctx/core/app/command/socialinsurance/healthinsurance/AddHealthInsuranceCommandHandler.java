package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddHealthInsuranceCommandHandler extends CommandHandler<AddHealthInsuranceCommand> {

    @Override
    protected void handle(CommandHandlerContext<AddHealthInsuranceCommand> commandHandlerContext) {

    }
}