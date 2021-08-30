package nts.uk.ctx.at.request.ws.dialog.employmentsystem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.holidaysubstitute.HolidaySubstituteDto;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.holidaysubstitute.HolidaySubstituteFinder;

@Path("at/request/employment")
@Produces("application/json")
public class HolidaySubstituteServices {
	
	@Inject
	private HolidaySubstituteFinder finder;
	
	@POST
	@Path("getHolidaySub")
	public HolidaySubstituteDto getEmployee(ParamKdl005 param)
	{		
		HolidaySubstituteDto result = finder.findHolidaySubstitute(param.getEmployeeIds());
		return result;
	}
	
}

@Data
class ParamKdl005{
	 List<String> employeeIds;
	 String baseDate;
}
