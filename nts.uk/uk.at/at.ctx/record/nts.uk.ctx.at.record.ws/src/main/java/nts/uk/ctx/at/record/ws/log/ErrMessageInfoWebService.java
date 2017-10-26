package nts.uk.ctx.at.record.ws.log;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.log.ErrMessageInfoFinder;
import nts.uk.ctx.at.record.app.find.log.dto.ErrMessageInfoDto;

@Path("at/record/message")
@Produces("application/json")
public class ErrMessageInfoWebService {
	
	@Inject
	private ErrMessageInfoFinder finder;
	
	@POST
	@Path("getall/{empCalAndSumExecLogID}")
	public List<ErrMessageInfoDto> getAllErrMessageInfoByEmpID(@PathParam("empCalAndSumExecLogID") String empCalAndSumExecLogID){
		return this.finder.getAllErrMessageInfoByEmpID(empCalAndSumExecLogID);
	}
	

}
