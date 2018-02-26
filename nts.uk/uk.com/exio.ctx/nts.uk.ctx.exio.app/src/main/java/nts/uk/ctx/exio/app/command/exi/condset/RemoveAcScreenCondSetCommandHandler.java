package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSetRepository;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;

@Stateless
@Transactional
public class RemoveAcScreenCondSetCommandHandler extends CommandHandler<AcScreenCondSetCommand>
{
    
    @Inject
    private AcScreenCondSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AcScreenCondSetCommand> context) {
        String cid = context.getCommand().getCid();
        String conditionSetCd = context.getCommand().getConditionSetCd();
        int acceptItemNum = context.getCommand().getAcceptItemNum();
        repository.remove(cid, conditionSetCd, acceptItemNum);
    }
}
