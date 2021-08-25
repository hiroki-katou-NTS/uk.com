package nts.uk.ctx.exio.app.input.execute;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.exio.dom.input.ExecuteImporting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportExecuteCommandHandler extends AsyncCommandHandler<ExternalImportExecuteCommand>{

	@Inject
	private ExternalImportExecuteRequire require;
	
	@Inject
	private TransactionService transaction;
	
	@Override
	protected void handle(CommandHandlerContext<ExternalImportExecuteCommand> context) {
		
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		val require = this.require.create(companyId);
		
		val currentState = require.getExternalImportCurrentState(companyId);
		currentState.execute(require, () -> run(require, command, companyId));
	}

	private void run(ExternalImportExecuteRequire.Require require,
			ExternalImportExecuteCommand command,
			String companyId) {
		
		Iterable<AtomTask> atomTasks = ExecuteImporting.execute(
				require,
				companyId,
				command.getExternalImportCode());
		
		transaction.separateForEachTask(atomTasks);
	}

}
