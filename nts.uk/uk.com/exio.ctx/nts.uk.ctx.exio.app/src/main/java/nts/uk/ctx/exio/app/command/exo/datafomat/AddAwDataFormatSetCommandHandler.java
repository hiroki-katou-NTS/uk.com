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
public class AddAwDataFormatSetCommandHandler extends CommandHandler<AwDataFormatSetCommand>
{
    
    @Inject
    private AwDataFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AwDataFormatSetCommand> context) {
        AwDataFormatSetCommand addCommand = context.getCommand();
        repository.add(new AwDataFormatSet(addCommand.getCid(), addCommand.getClosedOutput(), addCommand.getAbsenceOutput(), addCommand.getFixedValue(), addCommand.getValueOfFixedValue(), addCommand.getAtWorkOutput(), addCommand.getRetirementOutput()));
    
    }
}
