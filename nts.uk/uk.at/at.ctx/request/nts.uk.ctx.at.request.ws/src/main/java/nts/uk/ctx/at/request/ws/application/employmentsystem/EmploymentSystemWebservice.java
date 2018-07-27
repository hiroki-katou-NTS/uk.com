package nts.uk.ctx.at.request.ws.application.employmentsystem;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.employment.EmpRsvLeaveInforDto;
import nts.uk.ctx.at.request.app.find.application.employment.EmploymentReserveLeaveInforFinder;
import nts.uk.ctx.at.request.app.find.application.employment.ParamEmpRsvLeave;

@Path("at/request/application/employment")
@Produces("application/json")
public class EmploymentSystemWebservice extends WebService {
	@Inject
	private EmploymentReserveLeaveInforFinder empRsvLeaveFinder;
	
	@POST
	@Path("system")
	public EmpRsvLeaveInforDto getEmploymentSetting(ParamEmpRsvLeave param){
		 return empRsvLeaveFinder.getEmploymentReserveLeaveInfor(param);
	}
	@POST
	@Path("getByEmployee")
	public EmpRsvLeaveInforDto getByEmployee(ParamEmpRsvLeave param){
		 return empRsvLeaveFinder.getByEmloyee(param);
	}
	
}
