/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.statement.scrB;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.statement.AddStampingOutputItemSetCommand;
import nts.uk.ctx.at.function.app.command.statement.AddStampingOutputItemSetCommandHandler;
import nts.uk.ctx.at.function.app.command.statement.UpdateStampingOutputItemSetCommandHandler;
import nts.uk.ctx.at.function.app.find.statement.scrB.OutputItemSetDto;
import nts.uk.ctx.at.function.app.find.statement.scrB.StampingOutputItemSetFinder;

/**
 * The Class OutputConditionOfEmbossingWS.
 */
@Path("at/function/statement")
@Produces(MediaType.APPLICATION_JSON)
public class StampingOutputItemSetWS extends WebService{
	
	@Inject
	private StampingOutputItemSetFinder stampingOutputItemSetFinder;
	
	@Inject
	private AddStampingOutputItemSetCommandHandler addStampingOutputItemSetCommandHandler;
	
	@Inject
	private UpdateStampingOutputItemSetCommandHandler updateStampingOutputItemSetCommandHandler;
	
	/**
	 * Start page.
	 *
	 * @return the output condition of embossing dto
	 */
	@Path("findAll")
	@POST
	public List<OutputItemSetDto> startPage(){
		return  this.stampingOutputItemSetFinder.findAll();
	}
	
	/**
	 * Removes the stamping output item set.
	 *
	 * @param command the command
	 */
	@POST
	@Path("add")
	public void addStampingOutputItemSet(AddStampingOutputItemSetCommand command) {

		this.addStampingOutputItemSetCommandHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void updateStampingOutputItemSet(AddStampingOutputItemSetCommand command) {

		this.updateStampingOutputItemSetCommandHandler.handle(command);
	}
}
