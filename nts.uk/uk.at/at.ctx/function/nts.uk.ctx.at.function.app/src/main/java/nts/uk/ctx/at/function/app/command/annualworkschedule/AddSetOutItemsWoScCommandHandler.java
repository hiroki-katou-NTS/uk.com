package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddSetOutItemsWoScCommandHandler extends CommandHandler<SetOutItemsWoScCommand>
{
    
    @Inject
    private SetOutItemsWoScRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SetOutItemsWoScCommand> context) {
        SetOutItemsWoScCommand addCommand = context.getCommand();
        String companyId = AppContexts.user().companyId();
        repository.add(new SetOutItemsWoSc(companyId, new OutItemsWoScCode(addCommand.getCd()),
                       new OutItemsWoScName(addCommand.getName()),
                       addCommand.getOutNumExceedTime36Agr(), addCommand.getDisplayFormat()));
    }
}
