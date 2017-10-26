package nts.uk.ctx.at.record.ws.log;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.log.dto.ImplementationResultFinder;
import nts.uk.ctx.at.record.app.find.log.dto.ScreenImplementationResultDto;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;

/**
 * 
 * @author hieult
 *
 */	
@Path("at/record/log")
@Produces("application/json")
public class ImplementationResultWebService extends WebService{
	
	@Inject
	private ImplementationResultFinder implementationResultFinder;
	
	
	@POST
	@Path("findByEmpCalAndSumExecLogID")
	
	public ScreenImplementationResultDto getByEmpCalAndSumExecLogID(String empCalAndSumExecLogID){
		ScreenImplementationResultDto data  = 	implementationResultFinder.getScreenImplementationResult(empCalAndSumExecLogID);
		return data;
	}
}
