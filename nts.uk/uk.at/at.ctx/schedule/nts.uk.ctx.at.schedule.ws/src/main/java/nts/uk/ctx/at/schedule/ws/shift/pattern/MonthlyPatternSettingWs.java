/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.schedule.app.command.shift.pattern.MonthlyPatternSettingAddCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.MonthlyPatternSettingAddCommandHandler;

/**
 * The Class MonthlyPatternSettingWs.
 */
@Path("ctx/at/schedule/pattern/monthy/setting")
@Produces(MediaType.APPLICATION_JSON)
public class MonthlyPatternSettingWs {
	
	/** The add. */
	@Inject
	private  MonthlyPatternSettingAddCommandHandler add;
	
	/**
	 * Adds the.
	 *
	 * @param command the command
	 */
	@POST
	@Path("add")
	public void add( MonthlyPatternSettingAddCommand command){
		this.add.handle(command);
	}

}
