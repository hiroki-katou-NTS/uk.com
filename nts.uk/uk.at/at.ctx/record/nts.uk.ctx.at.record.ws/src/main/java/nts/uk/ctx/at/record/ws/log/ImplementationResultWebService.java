package nts.uk.ctx.at.record.ws.log;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.record.app.command.workrecord.log.ExecutionProcessingCommand;
import nts.uk.ctx.at.record.app.command.workrecord.log.QueryExecutionStatusCommandHandler;
import nts.uk.ctx.at.record.app.find.log.dto.ImplementationResultFinder;
import nts.uk.ctx.at.record.app.find.log.dto.ScreenImplementationResultDto;


/**
 * 
 * @author hieult
 *
 */	
@Path("/at/record/log")
@Produces("application/json")
public class ImplementationResultWebService extends WebService{
	
	@Inject
	private ImplementationResultFinder implementationResultFinder;
	
	@Inject
	private QueryExecutionStatusCommandHandler handler;
	
	
	@POST
	@Path("findByEmpCalAndSumExecLogID/{empCalAndSumExecLogID}")
	
	public ScreenImplementationResultDto getByEmpCalAndSumExecLogID(@PathParam("empCalAndSumExecLogID") String empCalAndSumExecLogID){
		ScreenImplementationResultDto data = implementationResultFinder.getScreenImplementationResult(empCalAndSumExecLogID);
		return data;
	}
	
	@POST
	@Path("asyncTask")
	public AsyncTaskInfo test() {
		return this.handler.handle(new ExecutionProcessingCommand());
	}
}
