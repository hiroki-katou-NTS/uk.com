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
public class AddDaiFuncControlCommandHandler extends CommandHandler<DaiFuncControlCommand>{

	@Inject
	private DaiFuncControlRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<DaiFuncControlCommand> context) {
		DaiFuncControlCommand addCommand = context.getCommand();
		DaiPerformanceFun daiPerformanceFun = DaiPerformanceFun.createFromJavaType(addCommand.getCid(), 
				addCommand.getComment(), 
				addCommand.isDisp36Atr() ? 1 : 0, 
				addCommand.isFlexDispAtr() ? 1 : 0, 
				addCommand.isCheckErrRefDisp()? 1 : 0);
		IdentityProcess identityProcess = IdentityProcess.createFromJavaType(addCommand.getCid(), 
				addCommand.isDaySelfChk() ? 1 :0, 
				addCommand.isMonSelfChK() ? 1 : 0, 
				addCommand.getDaySelfChkError());
		ApprovalProcess approvalProcess = ApprovalProcess.createFromJavaType(addCommand.getCid(),
				addCommand.isDayBossChk() ? 1 : 0, 
				addCommand.isMonBossChk() ? 1 : 0, 
				addCommand.getDayBossChkError());
		repository.add(daiPerformanceFun, identityProcess, approvalProcess);
	}

}
