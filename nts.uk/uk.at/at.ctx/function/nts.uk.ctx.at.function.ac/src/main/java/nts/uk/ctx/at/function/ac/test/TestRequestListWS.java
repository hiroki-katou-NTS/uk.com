package nts.uk.ctx.at.function.ac.test;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplaceInforExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

@Path("test-rqlist")
@Produces(MediaType.APPLICATION_JSON)
public class TestRequestListWS {
	@Inject
	private WorkplacePub workplacePub;

	@POST
	@Path("/559")
	public List<WorkplaceInforExport> get559(ParamTest1 param) {
		return workplacePub.getAllActiveWorkplaceInfor(param.companyId, param.baseDate);
	}
	
	@POST
	@Path("/560")
	public List<WorkplaceInforExport> get560(ParamTest2 param) {
		return workplacePub.getWorkplaceInforByWkpIds(param.companyId, param.listWorkplaceId, param.baseDate);
	}
	
	@POST
	@Path("/561")
	public List<WorkplaceInforExport> get560(ParamTest3 param) {
		return workplacePub.getPastWorkplaceInfor(param.companyId, param.historyId, param.listWorkplaceId);
	}
	
	@POST
	@Path("/567")
	public List<String> get567(ParamTest4 param) {
		return workplacePub.getAllChildrenOfWorkplaceId(param.companyId, param.baseDate, param.parentWorkplaceId);
	}
	
	@POST
	@Path("/573")
	public List<String> get573(ParamTest5 param) {
		return workplacePub.getWorkplaceIdAndChildren(param.companyId, param.baseDate, param.workplaceId);
	}
	
	
}
