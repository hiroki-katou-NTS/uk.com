package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;

@Stateless
@Transactional
public class UpdateApprovalProcessCommandHandler extends CommandHandler<ApprovalProcessCommand>
{
    
    @Inject
    private ApprovalProcessRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ApprovalProcessCommand> context) {
        ApprovalProcessCommand updateCommand = context.getCommand();
        repository.update(ApprovalProcess.createFromJavaType(updateCommand.getCid(), updateCommand.getJobTitleId(), 
    														updateCommand.isUseDailyBossChk() ? 1 : 0, updateCommand.isUseMonthBossChk() ? 1 : 0, 
											        		updateCommand.getSupervisorConfirmError()));
    
    }
}
