package nts.uk.ctx.exio.app.command.exi.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;

@Stateless
@Transactional
public class RemoveNumDataFormatSetCommandHandler extends CommandHandler<NumDataFormatSetCommand>
{
    
    @Inject
    private NumDataFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<NumDataFormatSetCommand> context) {
        String cid = context.getCommand().getCid();
        String conditionSetCd = context.getCommand().getConditionSetCd();
        int acceptItemNum = context.getCommand().getAcceptItemNum();
        repository.remove(cid, conditionSetCd, acceptItemNum);
    }
}
