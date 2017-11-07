package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPersonRepository;

@Stateless
@Transactional
public class ConfirmProcessingCommandHandler extends AsyncCommandHandler<ExecutionCommandResult> {

	@Inject
	EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	TargetPersonRepository targetPersonRepository ;
	
	@Override
	protected void handle(CommandHandlerContext<ExecutionCommandResult> context) {
		val asyncContext = context.asAsync();
		if (asyncContext.hasBeenRequestedToCancel()) {
			asyncContext.finishedAsCancelled();
			return;
		}
		//ドメインモデル「就業計算と集計実行ログ」を取得する (Láy dữ liệu từ domain 「就業計算と集計実行ログ」)
		Optional<EmpCalAndSumExeLog> empCalAndSumExeLog =empCalAndSumExeLogRepository.getByEmpCalAndSumExecLogID(context.getCommand().getEmpCalAndSumExecLogID());
		//ドメインモデル「対象者」を取得する (Lấy dữ liệu từ domain 「対象者」)
		List<TargetPerson> lstTargetPerson = targetPersonRepository.getByempCalAndSumExecLogID(context.getCommand().getEmpCalAndSumExecLogID());
		
		
		
	}
	
}
