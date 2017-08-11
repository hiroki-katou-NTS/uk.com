/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern.monthly;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.MonthlyPatternAddCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.MonthlyPatternAddCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.MonthlyPatternDeleteCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.MonthlyPatternDeleteCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.MonthlyPatternUpdateCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.MonthlyPatternUpdateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.work.WorkMonthlySettingDeleteCommand;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.work.WorkMonthlySettingDeleteCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.MonthlyPatternFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternDto;

/**
 * The Class MonthlyPatternWs.
 */
@Path("ctx/at/schedule/pattern/monthly")
@Produces(MediaType.APPLICATION_JSON)
public class MonthlyPatternWs extends WebService{

	/** The finder. */
	@Inject
	private MonthlyPatternFinder finder;
	
	/** The add. */
	@Inject
	private MonthlyPatternAddCommandHandler add;
	
	/** The update. */
	@Inject
	private MonthlyPatternUpdateCommandHandler update;
	
	/** The delete. */
	@Inject
	private MonthlyPatternDeleteCommandHandler delete;
	
	/** The delete work monthly. */
	@Inject
	private WorkMonthlySettingDeleteCommandHandler deleteWorkMonthly;
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<MonthlyPatternDto> findAll(){
		return this.finder.findAll();
	}
	
	/**
	 * Find by id.
	 *
	 * @param monthlyPatternCode the monthly pattern code
	 * @return the monthly pattern dto
	 */
	@POST
	@Path("findById/{monthlyPatternCode}")
	public MonthlyPatternDto findById(@PathParam("monthlyPatternCode") String monthlyPatternCode){
		return this.finder.findById(monthlyPatternCode);
	}
	
	
	/**
	 * Adds the.
	 *
	 * @param command the command
	 */
	@POST
	@Path("add")
	public void add(MonthlyPatternAddCommand command){
		this.add.handle(command);
	}
	
	/**
	 * Update.
	 *
	 * @param command the command
	 */
	@POST
	@Path("update")
	public void update(MonthlyPatternUpdateCommand command){
		this.update.handle(command);
	}
	
	/**
	 * Delete.
	 *
	 * @param command the command
	 */
	@POST
	@Path("delete")
	public void delete(MonthlyPatternDeleteCommand command){
		this.delete.handle(command);
		WorkMonthlySettingDeleteCommand commandWorkMonthly = new WorkMonthlySettingDeleteCommand();
		commandWorkMonthly.setMonthlyPattnernCode(command.getMonthlyPattnernCode());
		this.deleteWorkMonthly.handle(commandWorkMonthly);
	}
}
