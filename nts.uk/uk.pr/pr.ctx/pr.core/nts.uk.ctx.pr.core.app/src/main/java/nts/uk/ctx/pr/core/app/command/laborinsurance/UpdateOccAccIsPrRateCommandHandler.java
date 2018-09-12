package nts.uk.ctx.pr.core.app.command.laborinsurance;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRateRepository;

@Stateless
@Transactional
public class UpdateOccAccIsPrRateCommandHandler extends CommandHandler<OccAccIsPrRateCommand>
{
    
    @Inject
    private OccAccIsPrRateRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<OccAccIsPrRateCommand> context) {

    }
}
