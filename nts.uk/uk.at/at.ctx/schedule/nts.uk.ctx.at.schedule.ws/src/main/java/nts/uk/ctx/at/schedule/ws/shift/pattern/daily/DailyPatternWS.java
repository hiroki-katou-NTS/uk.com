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
import nts.uk.ctx.at.schedule.app.command.shift.pattern.daily.SaveDailyPatternCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.DailyPatternFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternDetailDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternItemDto;

/**
 * The Class DailyPatternWS.
 */
@Path("ctx/at/schedule/shift/pattern/daily")
@Produces("application/json")
public class DailyPatternWS extends WebService {

	/** The pattern calendar finder. */
	@Inject
	private DailyPatternFinder dailyPatternFinder;

	/** The patterb handler. */
	@Inject
	private SaveDailyPatternCommandHandler patternCommandHandler;

	/**
	 * Gets the allpatt calendar.
	 *
	 * @return the allpatt calendar
	 */
	@POST
	@Path("getall")
	public List<DailyPatternItemDto> getAllpattCalendar() {
		return this.dailyPatternFinder.getAllPattCalendar();
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(DailyPatternCommand command) {
		this.patternCommandHandler.handle(command);
	}

	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/{patternCd}")
	public DailyPatternDetailDto getDetailByCode(@PathParam("patternCd") String patternCd) {
		return this.dailyPatternFinder.findByCode(patternCd);
	}

	/**
	 * deleted bypattern cd.
	 *
	 * @param patternCd
	 *            the pattern cd
	 * @return the list
	 */
	@POST
	@Path("delete/{patternCd}")
	public void deledteByPatternCd(@PathParam("patternCd") String patternCd) {
		this.dailyPatternFinder.deleteByCode(patternCd);
	}
}
