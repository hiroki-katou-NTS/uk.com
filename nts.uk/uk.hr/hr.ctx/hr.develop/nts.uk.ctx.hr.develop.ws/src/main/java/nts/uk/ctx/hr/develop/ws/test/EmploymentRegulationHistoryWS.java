package nts.uk.ctx.hr.develop.ws.test;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.shr.com.context.AppContexts;
import test.FinderTest;
import test.dto.EmpRegulationHistListDto;
import test.dto.EmploymentRegulationHistoryDto;
import test.param.EmpRegHisParamDto;

@Path("empRegHis")
@Produces(MediaType.APPLICATION_JSON)
public class EmploymentRegulationHistoryWS {
	
	@Inject
	private FinderTest finder;
	
	@POST
	@Path("/getLatest")
	public Optional<EmploymentRegulationHistoryDto> getLatestEmpRegulationHist(){
		return finder.getLatestEmpRegulationHist(AppContexts.user().companyId());
	}
	
	@POST
	@Path("/getList")
	public EmpRegulationHistListDto getEmpRegulationHistList(EmpRegHisParamDto param){
		return finder.getEmpRegulationHistList(param.cId);
	}
	
	@POST
	@Path("/add")
	public String addEmpRegulationHist(EmpRegHisParamDto param){
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
}
