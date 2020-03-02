package nts.uk.ctx.hr.develop.ws.test;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import test.empregulationhistory.FinderTest;
import test.empregulationhistory.dto.EmpRegulationHistListDto;
import test.empregulationhistory.dto.EmploymentRegulationHistoryDto;
import test.empregulationhistory.param.EmpRegHisParamDto;

@Path("empRegHis")
@Produces(MediaType.APPLICATION_JSON)
public class EmploymentRegulationHistoryWS {
	
	@Inject
	private FinderTest finder;
	
	@POST
	@Path("/getLatest")
	public EmploymentRegulationHistoryDto getLatestEmpRegulationHist(EmpRegHisParamDto param){
		Optional<EmploymentRegulationHistoryDto> result = finder.getLatestEmpRegulationHist(param.cId);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	
	@POST
	@Path("/getList")
	public EmpRegulationHistListDto getEmpRegulationHistList(EmpRegHisParamDto param){
		return finder.getEmpRegulationHistList(param.cId);
	}
	
	@POST
	@Path("/add")
	public EmpRegHisParamDto addEmpRegulationHist(EmpRegHisParamDto param){
		return finder.addEmpRegulationHist(param.cId, param.startDate);
	}
	
	@POST
	@Path("/update")
	public void updateEmpRegulationHist(EmpRegHisParamDto param){
		finder.updateEmpRegulationHist(param.cId, param.historyId, param.startDate);
	}
	
	@POST
	@Path("/remove")
	public void removeEmpRegulationHist(EmpRegHisParamDto param){
		finder.removeEmpRegulationHist(param.cId, param.historyId);
	}
	
	@POST
	@Path("/getByDate")
	public EmpRegHisParamDto getHistoryIdByDate(EmpRegHisParamDto param){
		return finder.getHistoryIdByDate(param.cId, param.startDate);
	}
}
