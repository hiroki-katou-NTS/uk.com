package nts.uk.ctx.at.record.ws.log;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.log.EmpCalAndSumExeLogFinder;
import nts.uk.ctx.at.record.app.find.log.dto.EmpCalAndSumExeLogDto;
import nts.uk.ctx.at.record.app.find.log.dto.InputEmpCalAndSum;

@Path("at/record/log")
@Produces("application/json")
public class EmpCalAndSumExeLogWebService extends WebService {
	
	@Inject
	private EmpCalAndSumExeLogFinder empCalAndSumExeLogFinder;
	/**
	 * get all EmpCalAndSumExeLog
	 * @param operationCaseID
	 * @return
	 */
	@POST
	@Path("getall/{operationCaseID}")
	public List<EmpCalAndSumExeLogDto> getAllEmpCalAndSumExeLog(@PathParam("operationCaseID") String operationCaseID){
		return this.empCalAndSumExeLogFinder.getAllEmpCalAndSumExeLog(operationCaseID);
	}
	/**
	 * get EmpCalAndSumExeLog by code
	 * @param inputEmpCalAndSum
	 * @return
	 */
	@POST
	@Path("getbycode")
	public EmpCalAndSumExeLogDto getEmpCalAndSumExeLogByID(InputEmpCalAndSum inputEmpCalAndSum){
		return this.empCalAndSumExeLogFinder.getEmpCalAndSumExeLogById(inputEmpCalAndSum);
	}
	
}
