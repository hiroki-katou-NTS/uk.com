package nts.uk.ctx.at.request.ws.dialog.employmentsystem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmploymentSystemFinder;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.holidaysubstitute.HolidaySubstituteDto;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.holidaysubstitute.HolidaySubstituteFinder;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/employment")
@Produces("application/json")
public class HolidaySubstituteServices {
	
	@Inject
	private HolidaySubstituteFinder finder;
	
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;
	
	@POST
	@Path("getHolidaySub")
	public HolidaySubstituteDto getHolidaySubstitute(ParamKdl005 param) {		
		HolidaySubstituteDto result = finder.findHolidaySubstitute(param.getEmployeeIds());
		return result;
	}
	
	@POST
	@Path("getSid")
	public List<String> getEmployee() {		
		List<String> result = employeeRequestAdapter.getAllSidByCid(AppContexts.user().companyId());
		return result;
	}
	
}

@Data
class ParamKdl005{
	 List<String> employeeIds;
	 String baseDate;
}
