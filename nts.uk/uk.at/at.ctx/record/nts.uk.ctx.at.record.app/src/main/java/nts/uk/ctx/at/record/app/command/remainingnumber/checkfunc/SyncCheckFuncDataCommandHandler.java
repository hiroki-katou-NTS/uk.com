package nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc;

import java.util.concurrent.TimeUnit;

import javax.ejb.Stateful;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;

@Stateful
public class SyncCheckFuncDataCommandHandler extends AsyncCommandHandler<CheckFuncDataCommand> {
	
	private static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";
	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";
	private static final String NUMBER_OF_TOTAL = "NUMBER_OF_TOTAL";
	private static final String ERROR_LIST = "ERROR_LIST";
	
	@Override
	protected void handle(CommandHandlerContext<CheckFuncDataCommand> context) {
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		
		CheckFuncDataCommand command = context.getCommand();
		setter.setData(NUMBER_OF_SUCCESS, command.getPass());
		setter.setData(NUMBER_OF_ERROR, command.getError());
		setter.setData(NUMBER_OF_TOTAL, command.getTotal());
		setter.setData(ERROR_LIST, command.getOutputErrorList());
		
		for (int i = 1; i < command.getTotal(); i++) {

			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				break;
			}

			if(i %5 == 0){
				setter.updateData(NUMBER_OF_ERROR, i/5);
			}
			setter.updateData(NUMBER_OF_SUCCESS, i);
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
