package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;

@Stateless
@Transactional
public class RemoveStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand>
{
    
    @Inject
    private StdOutputCondSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
        String cid = context.getCommand().getCid();
        String conditionSetCd = context.getCommand().getConditionSetCd();
        repository.remove(cid, conditionSetCd);
    }
}
