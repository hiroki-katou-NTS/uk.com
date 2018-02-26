package nts.uk.ctx.exio.app.command.exi.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;

@Stateless
@Transactional
public class RemoveInsTimeDatFmSetCommandHandler extends CommandHandler<InsTimeDatFmSetCommand>
{
    
    @Inject
    private InsTimeDatFmSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<InsTimeDatFmSetCommand> context) {
        String cid = context.getCommand().getCid();
        String conditionSetCd = context.getCommand().getConditionSetCd();
        int acceptItemNum = context.getCommand().getAcceptItemNum();
        repository.remove(cid, conditionSetCd, acceptItemNum);
    }
}
