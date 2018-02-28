package nts.uk.ctx.exio.app.command.exi.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;

@Stateless
@Transactional
public class AddDateDataFormSetCommandHandler extends CommandHandler<DateDataFormSetCommand>
{
    
    @Inject
    private DateDataFormSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<DateDataFormSetCommand> context) {
        DateDataFormSetCommand addCommand = context.getCommand();
        repository.add(DateDataFormSet.createFromJavaType(0L, addCommand.getCid(), addCommand.getConditionSetCd(), addCommand.getAcceptItemNum(), addCommand.getFixedValue(), addCommand.getValueOfFixedValue(), addCommand.getFormatSelection()));
    
    }
}
