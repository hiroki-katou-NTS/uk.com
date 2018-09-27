/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.statement.scrB;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.app.command.statement.AddStampingOutputItemSetCommand;
import nts.uk.ctx.at.function.app.command.statement.AddStampingOutputItemSetCommandHandler;
import nts.uk.ctx.at.function.app.command.statement.DeleteStampingOutputItemSetCommand;
import nts.uk.ctx.at.function.app.command.statement.DeleteStampingOutputItemSetCommandHandler;
import nts.uk.ctx.at.function.app.command.statement.UpdateStampingOutputItemSetCommandHandler;
import nts.uk.ctx.at.function.app.find.statement.export.DataExport;
import nts.uk.ctx.at.function.app.find.statement.export.StatementList;
import nts.uk.ctx.at.function.app.find.statement.outputitemsetting.OutputConditionDto;
import nts.uk.ctx.at.function.app.find.statement.outputitemsetting.OutputItemSetDto;
import nts.uk.ctx.at.function.app.find.statement.outputitemsetting.StamOutputEnumDto;
import nts.uk.ctx.at.function.app.find.statement.outputitemsetting.StampingOutputItemSetFinder;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

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
	
	@Inject
	private DeleteStampingOutputItemSetCommandHandler deleteStampingOutputItemSetCommandHandler;
	
	/** The i 18 n. */
	@Inject
	private I18NResourcesForUK i18n;
	
	@Inject
	private DataExport export;
	
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
	 * Find stamp output.
	 *
	 * @param stampCode the stamp code
	 * @return the output item set dto
	 */
	@Path("findStampingOutput/{stampCode}")
	@POST
	public OutputItemSetDto findStampOutput(@PathParam("stampCode") String stampCode){
		return  this.stampingOutputItemSetFinder.findStampOutput(stampCode);
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
	
	/**
	 * Update stamping output item set.
	 *
	 * @param command the command
	 */
	@POST
	@Path("update")
	public void updateStampingOutputItemSet(AddStampingOutputItemSetCommand command) {

		this.updateStampingOutputItemSetCommandHandler.handle(command);
	}
	
	/**
	 * Delete stamping output item set.
	 *
	 * @param command the command
	 */
	@POST
	@Path("delete")
	public void deleteStampingOutputItemSet(DeleteStampingOutputItemSetCommand command) {

		this.deleteStampingOutputItemSetCommandHandler.handle(command);
	}
	
	@POST
	@Path("export")
	public List<StatementList> exportData(OutputConditionDto dto) {
		return export.getTargetData(dto.getLstEmployee(),GeneralDate.fromString(dto.getStartDate(), "yyyy/MM/dd"),GeneralDate.fromString(dto.getEndDate(), "yyyy/MM/dd"),dto.isCardNumNotRegister());
	}
	
	/**
	 * Gets the all enum.
	 *
	 * @return the all enum
	 */
	@Path("getAllEnum")
	@POST
	public StamOutputEnumDto getAllEnum() {
		return StamOutputEnumDto.init(i18n);
	}
}
