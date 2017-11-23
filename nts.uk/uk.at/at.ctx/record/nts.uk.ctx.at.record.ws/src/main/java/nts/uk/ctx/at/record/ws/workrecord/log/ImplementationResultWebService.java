package nts.uk.ctx.at.record.ws.workrecord.log;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.record.app.command.workrecord.log.AddEmpCalSumAndTargetCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.log.CheckingProcessingResult;
import nts.uk.ctx.at.record.app.command.workrecord.log.ExecutionCommandResult;
import nts.uk.ctx.at.record.app.command.workrecord.log.ExecutionProcessingCommand;
import nts.uk.ctx.at.record.app.command.workrecord.log.QueryExecutionStatusCommandHandler;
import nts.uk.ctx.at.record.app.find.log.ImplementationResultFinder;
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
	private QueryExecutionStatusCommandHandler queryExecutionStatusCommandHandler;
	
	@Inject 
	private AddEmpCalSumAndTargetCommandHandler addEmpCalSumAndTargetCommandHandler;
	
	@POST
	@Path("findByEmpCalAndSumExecLogID/{empCalAndSumExecLogID}")
	public ScreenImplementationResultDto getByEmpCalAndSumExecLogID(@PathParam("empCalAndSumExecLogID") String empCalAndSumExecLogID){
		ScreenImplementationResultDto data = implementationResultFinder.getScreenImplementationResult(empCalAndSumExecLogID);
		return data;
	}
	
	@POST
	@Path("addEmpCalSumAndTarget")
	public ExecutionCommandResult addEmpCalSumAndTarget(ExecutionProcessingCommand command) {
		return this.addEmpCalSumAndTargetCommandHandler.handle(command);
	}
	
	@POST
	@Path("executeTask")
	public CheckingProcessingResult executeTask(ExecutionCommandResult command) {
		return this.queryExecutionStatusCommandHandler.handle(command);
	}
}
	