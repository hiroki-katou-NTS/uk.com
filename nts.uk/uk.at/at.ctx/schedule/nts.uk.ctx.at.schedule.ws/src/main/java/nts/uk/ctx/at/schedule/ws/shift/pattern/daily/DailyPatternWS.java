/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern.daily;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.daily.DailyPatternCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.daily.DailyPatternCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.DailyPatternFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternDto;

/**
 * The Class DailyPatternWS.
 */
@Path("ctx/at/share/vacation/setting/patterncalendar/")
@Produces("application/json")
public class DailyPatternWS extends WebService {

	/** The pattern calendar finder. */
	@Inject
	private DailyPatternFinder patternCalendarFinder;

	/** The patterb handler. */
	@Inject
	private DailyPatternCommandHandler patterbHandler;

	/**
	 * Gets the allpatt calendar.
	 *
	 * @return the allpatt calendar
	 */
	@POST
	@Path("getallpattcal")
	public List<DailyPatternDto> getAllpattCalendar() {
		return this.patternCalendarFinder.getAllPattCalendar();
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("addpattcal")
	public void save(DailyPatternCommand command) {
		this.patterbHandler.handle(command);
	}

	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/setting")
	public List<DailyPatternDto> findByCompanyId() {
		return this.patternCalendarFinder.findPatternCalendarByCompanyId();
	}
	/**
	 * deleted bypattern cd.
	 *
	 * @param patternCd the pattern cd
	 * @return the list
	 */
	@POST
	@Path("deleted/pattern/{patternCd}")
	public void deledtedBypatternCd(@PathParam("patternCd") String patternCd) {
		this.patternCalendarFinder.deleted(patternCd);
	}
}
