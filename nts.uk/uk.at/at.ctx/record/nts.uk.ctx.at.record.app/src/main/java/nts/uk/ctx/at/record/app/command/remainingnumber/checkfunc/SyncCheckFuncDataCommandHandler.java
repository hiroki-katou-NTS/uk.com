package nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc;

import java.util.concurrent.TimeUnit;

import javax.ejb.Stateful;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;

@Stateful
public class SyncCheckFuncDataCommandHandler extends AsyncCommandHandler<CheckFuncDataCommand> {
	
	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";
	
	@Override
	protected void handle(CommandHandlerContext<CheckFuncDataCommand> context) {
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		
		CheckFuncDataCommand command = context.getCommand();
		setter.setData(NUMBER_OF_SUCCESS, command.getPass());
		
		for (int i = 1; i < command.getTotal(); i++) {

			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				break;
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
