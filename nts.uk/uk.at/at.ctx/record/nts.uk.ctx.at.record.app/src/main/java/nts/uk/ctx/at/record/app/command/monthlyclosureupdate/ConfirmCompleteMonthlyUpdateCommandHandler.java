package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class ConfirmCompleteMonthlyUpdateCommandHandler extends CommandHandler<String>{

	@Inject
	private MonthlyClosureUpdateLogRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<String> context) {
		MonthlyClosureUpdateLog log = repo.getLogById(context.getCommand()).get();
		log.updateExecuteStatus(MonthlyClosureExecutionStatus.COMPLETED_CONFIRMED);
		repo.updateStatus(log);
	}

}
