package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;
import nts.uk.shr.sample.asyncmd.SampleCancellableAsyncCommand;

@Stateless
public class QueryExecutionStatusCommandHandler extends AsyncCommandHandler<QueryExecutionStatusCommand> {

	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo;

	@Inject
	TargetPersonRepository targetPersonRepo;
	
	

	@Override
	protected void handle(CommandHandlerContext<QueryExecutionStatusCommand> context) {
		
		
		
		
		val asyncContext = context.asAsync();
		for (int i = 0; i < 10; i++) {
			// user requested to cancel task
			if (asyncContext.hasBeenRequestedToCancel()) {
				/* do something to clean up */
				// cancel explicitly
				asyncContext.finishedAsCancelled();
				break;
			}
			// some heavy task
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
