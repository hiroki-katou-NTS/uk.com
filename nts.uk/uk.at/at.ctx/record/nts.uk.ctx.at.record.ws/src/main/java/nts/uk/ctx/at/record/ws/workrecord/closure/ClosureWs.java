/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.workrecord.closure;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.workrecord.closure.ClosureSaveCommand;
import nts.uk.ctx.at.record.app.command.workrecord.closure.ClosureSaveCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.closure.ClosureFinder;
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureFindDto;

/**
 * The Class ClosureWs.
 */
@Path("ctx/at/record/workrecord/closure")
@Produces("application/json")
public class ClosureWs {
	
	/** The finder. */
	@Inject
	private ClosureFinder finder;
	
	/** The save. */
	private ClosureSaveCommandHandler save;
	
	
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@POST
	@Path("getall")
	public List<ClosureFindDto> getAll(){
		return this.finder.findAll();
	}
	
	
	/**
	 * Detail.
	 *
	 * @param closureId the closure id
	 * @return the closure find dto
	 */
	@POST
	@Path("detail/{closureId}")
	public ClosureFindDto detail(@PathParam("closureId") int closureId){
		return this.finder.getByClosure(closureId);
	}
	
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(ClosureSaveCommand command){
		this.save.handle(command);
	}
	
	
}
