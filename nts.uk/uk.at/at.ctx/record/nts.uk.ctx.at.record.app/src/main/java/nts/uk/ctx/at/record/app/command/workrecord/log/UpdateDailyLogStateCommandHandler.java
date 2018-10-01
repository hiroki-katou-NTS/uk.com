package nts.uk.ctx.at.record.app.command.workrecord.log;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;

@Stateless
public class UpdateDailyLogStateCommandHandler extends CommandHandler<String> {
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		
		this.empCalAndSumExeLogRepository.updateStatus(context.getCommand(), ExeStateOfCalAndSum.START_INTERRUPTION.value);
		
	}

}
