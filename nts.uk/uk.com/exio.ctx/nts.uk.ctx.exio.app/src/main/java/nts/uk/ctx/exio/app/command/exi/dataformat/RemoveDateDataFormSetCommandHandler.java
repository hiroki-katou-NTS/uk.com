package nts.uk.ctx.exio.app.command.exi.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;

@Stateless
@Transactional
public class RemoveDateDataFormSetCommandHandler extends CommandHandler<DateDataFormSetCommand>
{
    
    @Inject
    private DateDataFormSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<DateDataFormSetCommand> context) {
        String cid = context.getCommand().getCid();
        String conditionSetCd = context.getCommand().getConditionSetCd();
        int acceptItemNum = context.getCommand().getAcceptItemNum();
        repository.remove(cid, conditionSetCd, acceptItemNum);
    }
}
