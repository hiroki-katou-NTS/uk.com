package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;

import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveSetOutItemsWoScCommandHandler extends CommandHandler<SetOutItemsWoScCommand>
{
    
    @Inject
    private SetOutItemsWoScRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SetOutItemsWoScCommand> context) {
        //String cid = context.getCommand().getCid();
    	String cid = AppContexts.user().companyId();
        int cd = context.getCommand().getCd();
        repository.remove(cid, cd);
    }
}
