package nts.uk.ctx.exio.app.command.exo.datafomat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.datafomat.AwDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exo.datafomat.AwDataFormatSet;

@Stateless
@Transactional
public class UpdateAwDataFormatSetCommandHandler extends CommandHandler<AwDataFormatSetCommand>
{
    
    @Inject
    private AwDataFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AwDataFormatSetCommand> context) {
        AwDataFormatSetCommand updateCommand = context.getCommand();
        repository.update( new AwDataFormatSet(updateCommand.getCid(), updateCommand.getClosedOutput(), updateCommand.getAbsenceOutput(), updateCommand.getFixedValue(), updateCommand.getValueOfFixedValue(), updateCommand.getAtWorkOutput(), updateCommand.getRetirementOutput()));
    
    }
}
