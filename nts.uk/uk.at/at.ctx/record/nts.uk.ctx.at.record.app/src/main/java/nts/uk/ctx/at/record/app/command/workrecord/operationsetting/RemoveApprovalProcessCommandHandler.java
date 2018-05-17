package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;

@Stateless
@Transactional
public class RemoveApprovalProcessCommandHandler extends CommandHandler<ApprovalProcessCommand>
{
    
    @Inject
    private ApprovalProcessRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ApprovalProcessCommand> context) {
        String cid = context.getCommand().getCid();
        repository.remove(cid);
    }
}
