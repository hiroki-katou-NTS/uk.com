package nts.uk.shr.sample.batchserver;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;

@Path("batch")
@Produces("application/json")
public class BatchServerWebService {
	
	@Inject
	private BatchTask task;

	@POST
	@Path("task")
	public JavaTypeResult<String> doTask() {
		task.doSomething();
		return new JavaTypeResult<>("batch server done!");
	}
	
}
