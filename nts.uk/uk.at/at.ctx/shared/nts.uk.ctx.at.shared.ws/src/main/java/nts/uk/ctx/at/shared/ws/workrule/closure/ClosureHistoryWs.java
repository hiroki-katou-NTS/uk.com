/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.workrule.closure;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.workrule.closure.ClosureEmpAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.closure.ClosureHistoryAddCommand;
import nts.uk.ctx.at.shared.app.command.workrule.closure.ClosureHistoryAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.closure.ClosureHistorySaveCommand;
import nts.uk.ctx.at.shared.app.command.workrule.closure.ClosureHistorySaveCommandHandler;
import nts.uk.ctx.at.shared.app.command.workrule.closure.ClousureEmpAddCommand;
import nts.uk.ctx.at.shared.app.find.workrule.closure.ClosureHistoryFinder;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureEmployDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryHeaderDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryInDto;

/**
 * The Class ClosureHistoryWs.
 */
@Path("ctx/at/shared/workrule/closure/history")
@Produces("application/json")
public class ClosureHistoryWs {

	/** The finder. */
	@Inject
	private ClosureHistoryFinder finder;
	
	
	/** The save. */
	@Inject
	private ClosureHistorySaveCommandHandler save;
	
	/** The add. */
	@Inject
	private ClosureHistoryAddCommandHandler add;
	
	@Inject
	private ClosureEmpAddCommandHandler closureEmpAdd;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<ClosureHistoryFindDto> findAll(){
		return this.finder.findAll();
	}
	
	/**
	 * Find by id.
	 *
	 * @param master the master
	 * @return the closure history header dto
	 */
	@POST
	@Path("findById")
	public ClosureHistoryHeaderDto findById(ClosureHistoryInDto master){
		return this.finder.findById(master);
	}
	
	/**
	 * Gets the all.
	 *
	 * @param command the command
	 * @return the all
	 */
	@POST
	@Path("save")
	public void save(ClosureHistorySaveCommand command){
		 this.save.handle(command);
	}
	
	/**
	 * Adds the.
	 *
	 * @param command the command
	 */
	@POST
	@Path("add")
	public void add(ClosureHistoryAddCommand command){
		this.add.handle(command);
	}
	@POST
	@Path("getClosureEmploy")
	public ClosureEmployDto getClosureEmploy() {
		return this.finder.getClosureEmploy();
	}
	
	@POST
	@Path("addClousureEmp")
	public void addClousureEmp(ClousureEmpAddCommand command) {
		this.closureEmpAdd.handle(command);
	}
}
