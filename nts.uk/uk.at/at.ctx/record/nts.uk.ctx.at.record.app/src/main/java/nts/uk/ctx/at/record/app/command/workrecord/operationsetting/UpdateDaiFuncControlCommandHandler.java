package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiFuncControlRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;

@Stateless
@Transactional
public class UpdateDaiFuncControlCommandHandler extends CommandHandler<DaiFuncControlCommand>{

	@Inject
	private DaiFuncControlRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<DaiFuncControlCommand> context) {
		DaiFuncControlCommand updateCommand = context.getCommand();
		DaiPerformanceFun daiPerformanceFun = DaiPerformanceFun.createFromJavaType(updateCommand.getCid(), 
				updateCommand.getComment(), 
	    		updateCommand.isDisp36Atr() ? 1 : 0, 
	    		updateCommand.isFlexDispAtr() ? 1 : 0, 
				updateCommand.isCheckErrRefDisp()? 1 : 0);
		IdentityProcess identityProcess = IdentityProcess.createFromJavaType(updateCommand.getCid(), 
        		updateCommand.isDaySelfChk() ? 1 :0, 
        		updateCommand.isMonSelfChK() ? 1 : 0, 
        		updateCommand.getDaySelfChkError());
		ApprovalProcess approvalProcess = ApprovalProcess.createFromJavaType(updateCommand.getCid(),
				updateCommand.isDayBossChk() ? 1 : 0, 
				updateCommand.isMonBossChk() ? 1 : 0, 
        		updateCommand.getDayBossChkError());
		repository.update(daiPerformanceFun, identityProcess, approvalProcess);
	}

}
