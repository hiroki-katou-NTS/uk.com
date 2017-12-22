package nts.uk.ctx.at.shared.ws.calculation.holiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.AddHolidayAddtimeCommand;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.AddHolidayAddtimeCommandHandler;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.HolidayAddtimeDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.HolidayAddtimeFinder;

@Path("shared/caculation/holiday")
@Produces("application/json")
public class HolidayAddtimeWebService extends WebService{

	@Inject
	private HolidayAddtimeFinder holidayAddtimeFinder;
	
	@Inject
	private AddHolidayAddtimeCommandHandler addtimeCommandHandler;
	
	@Path("findByCid")
	@POST
	public List<HolidayAddtimeDto> findByCid() {
		return holidayAddtimeFinder.findAllHolidayAddtime();
	}
	
	@Path("add")
	@POST
	public JavaTypeResult<List<String>> add(AddHolidayAddtimeCommand command) {
		return new JavaTypeResult<List<String>>(this.addtimeCommandHandler.handle(command));
	}
	
	@Path("update")
	@POST
	public JavaTypeResult<List<String>> update(AddHolidayAddtimeCommand command) {
		return new JavaTypeResult<List<String>>(this.addtimeCommandHandler.handle(command));
	}
}
