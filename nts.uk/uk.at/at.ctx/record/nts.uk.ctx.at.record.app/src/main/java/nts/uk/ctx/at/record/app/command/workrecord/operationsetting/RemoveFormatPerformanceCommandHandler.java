package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformanceRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;

@Stateless
@Transactional
public class RemoveFormatPerformanceCommandHandler extends CommandHandler<FormatPerformanceCommand>
{
    
    @Inject
    private FormatPerformanceRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<FormatPerformanceCommand> context) {
        String cid = context.getCommand().getCid();
        repository.remove(cid);
    }
}
