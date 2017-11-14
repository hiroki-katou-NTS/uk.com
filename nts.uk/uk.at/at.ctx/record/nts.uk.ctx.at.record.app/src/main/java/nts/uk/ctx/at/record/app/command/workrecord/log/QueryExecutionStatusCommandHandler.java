package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;

@Stateless
@Transactional
public class QueryExecutionStatusCommandHandler extends AsyncCommandHandler<ExecutionCommandResult> {
	
	@Inject TargetPersonRepository targetPersonRepository;

	@Override
	protected void handle(CommandHandlerContext<ExecutionCommandResult> context) {
		val command = context.getCommand();
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
		
		// Get list TargetPerson
		List<TargetPerson> lstTargetPerson = targetPersonRepository.getByempCalAndSumExecLogID(command.getEmpCalAndSumExecLogID());
		
		// Process all TargetPersons
		for (TargetPerson targetPerson : lstTargetPerson) {
			// Handle cancel request
			if (asyncContext.hasBeenRequestedToCancel()) {
				asyncContext.finishedAsCancelled();
				break;
			}
			
			// TODO : アルゴリズム「D：bat統括プログラムを起動する」を実行する | Chạy xử lí bat
		}
	}
	
}
