package nts.uk.ctx.at.record.ws.workrecord.log.batchserver;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.task.AsyncTaskInfo;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.at.record.app.command.workrecord.log.CheckProcessCommand;
import nts.uk.ctx.at.record.ws.workrecord.log.ImplementationResultWebService;
import nts.uk.shr.com.communicate.PathToWebApi;
import nts.uk.shr.com.communicate.batch.BatchServer;

@Stateless
public class ImplResultBatchTask {
	
	@Inject
	private BatchServer batchServer;
	
	@Inject
	private ImplementationResultWebService implementationResultWebService;
	
	public AsyncTaskInfo execute(CheckProcessCommand command) {
		
		MutableValue<AsyncTaskInfo> result = new MutableValue<>();
		
		if (this.batchServer.exists()) {
			val webApi = this.batchServer.webApi(PathToWebApi.com("/batch/task"), CheckProcessCommand.class, AsyncTaskInfo.class);
			this.batchServer.request(webApi, c -> c.entity(command)
					.succeeded(x -> {
						result.set(x);
			}));
		} else {
			result.set(implementationResultWebService.executeTask(command));
		}
		
		return result.get();
		
	}

}
