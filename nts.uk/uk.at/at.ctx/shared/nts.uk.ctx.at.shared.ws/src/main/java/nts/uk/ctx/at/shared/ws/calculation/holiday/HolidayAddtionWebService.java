/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.calculation.holiday;
/**
 */
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.AddHolidayAddtimeCommand;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.AddHolidayAddtimeCommandHandler;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.HolidayAddtionDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.HolidayAddtionFinder;

/**
 * The Class HolidayAddtionWebService.
 */
@Path("shared/caculation/holiday")
@Produces("application/json")
public class HolidayAddtionWebService extends WebService{

	@Inject
	private HolidayAddtionFinder holidayAddtimeFinder;
	
	@Inject
	private AddHolidayAddtimeCommandHandler addtimeCommandHandler;
	
	@Path("findByCid")
	@POST
	public List<HolidayAddtionDto> findByCid() {
		return holidayAddtimeFinder.findAllHolidayAddtime();
	}
	
	@Path("add")
	@POST
	public void add(AddHolidayAddtimeCommand command) {
		this.addtimeCommandHandler.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(AddHolidayAddtimeCommand command) {
		this.addtimeCommandHandler.handle(command);
	}
}
