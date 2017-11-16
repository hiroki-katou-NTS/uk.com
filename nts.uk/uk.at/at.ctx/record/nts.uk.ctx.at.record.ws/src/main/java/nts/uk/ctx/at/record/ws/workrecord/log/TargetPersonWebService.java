package nts.uk.ctx.at.record.ws.workrecord.log;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.log.TargetPersonFinder;
import nts.uk.ctx.at.record.app.find.log.dto.TargetPersonDto;

@Path("at/record/target")
@Produces("application/json")
public class TargetPersonWebService {
	@Inject
	private TargetPersonFinder finder;
	/**
	 * get All TargetPerson
	 * @return
	 */
	@POST
	@Path("getall")
	public List<TargetPersonDto> getAllTargetPerson(){
		return this.finder.getAllTargetPerson();
	}
	/**
	 * get TargetPerson by code
	 * @param empCalAndSumExecLogId
	 * @return
	 */
	@POST
	@Path("getbycode/{empCalAndSumExecLogId}")
	public TargetPersonDto getTargetPersonById(@PathParam("empCalAndSumExecLogId") String empCalAndSumExecLogId){
		return this.finder.getTargetPersonByID(empCalAndSumExecLogId);
	}
	
	/**
	 * get list TargetPerson by empCalAndSumExecLogId
	 * @param empCalAndSumExecLogId
	 * @return
	 */
	@POST
	@Path("getbyempid/{empCalAndSumExecLogId}")
	public List<TargetPersonDto> getTargetPersonByEmpId(@PathParam("empCalAndSumExecLogId") String empCalAndSumExecLogId){
		return this.finder.getListTargetPersonByEmpId(empCalAndSumExecLogId);
	}
}
