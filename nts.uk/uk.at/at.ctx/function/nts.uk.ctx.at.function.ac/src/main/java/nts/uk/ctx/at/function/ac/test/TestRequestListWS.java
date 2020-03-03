package nts.uk.ctx.at.function.ac.test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.pub.workplace.config.WorkPlaceConfigPub;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplaceInforExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

@Path("test-rqlist")
@Produces(MediaType.APPLICATION_JSON)
public class TestRequestListWS {
	@Inject
	private WorkplacePub workplacePub;

	@Inject
	private WorkPlaceConfigPub workPlaceConfigPub;

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
	@Path("/569")
	public List<String> get569(ParamTest5 param) {
		return workplacePub.getUpperWorkplace(param.companyId, param.workplaceId, param.baseDate);
	}

	@POST
	@Path("/571")
	public List<String> get571(ParamTest5 param) {
		return workplacePub.getWorkplaceIdAndUpper(param.companyId, param.baseDate, param.workplaceId);
	}

	@POST
	@Path("/573")
	public List<String> get573(ParamTest5 param) {
		return workplacePub.getWorkplaceIdAndChildren(param.companyId, param.baseDate, param.workplaceId);
	}

	@POST
	@Path("/647")
	public List<WorkPlaceConfigDto> get647(ParamTest6 param) {
		return workPlaceConfigPub.findByCompanyIdAndPeriod(param.companyId, new DatePeriod(param.start, param.end)).stream()
				.map(x -> x.getWkpConfigHistory().stream().map(y -> new WorkPlaceConfigDto(x.getCompanyId(), y.getHistoryId(), y.getPeriod().start(), y.getPeriod().end()))
						.collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());

	}

}
