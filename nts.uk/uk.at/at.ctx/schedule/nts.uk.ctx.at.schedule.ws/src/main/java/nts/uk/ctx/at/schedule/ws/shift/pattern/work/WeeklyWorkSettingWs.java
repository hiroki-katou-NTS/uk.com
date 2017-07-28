/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern.work;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.WeeklyWorkSettingFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WeeklyWorkSettingAllDto;

/**
 * The Class WeeklyWorkSettingWs.
 */
@Path("ctx/at/schedule/pattern/work/weekly/setting")
@Produces(MediaType.APPLICATION_JSON)
public class WeeklyWorkSettingWs extends WebService {
	
	/** The finder. */
	@Inject
	private  WeeklyWorkSettingFinder finder;
	
	

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public WeeklyWorkSettingAllDto findAll(){
		return this.finder.findAll();
	}
}
