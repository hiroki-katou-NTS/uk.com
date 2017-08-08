/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.budget.external;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.external.DeleteExternalBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.external.DeleteExternalBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.InsertExternalBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.external.InsertExternalBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.UpdateExternalBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.external.UpdateExternalBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.ExecutionProcessCommand;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.ExecutionProcessCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetFinder;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetDataPreviewDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetExtractCondition;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetQuery;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.CompletionState;

/**
 * The Class ExternalBudgetWebService.
 */
@Path("at/schedule/budget/external")
@Produces("application/json")
public class ExternalBudgetWebService extends WebService {
	
	/** The find. */
	@Inject
	private ExternalBudgetFinder find;
	
	/** The insert. */
	@Inject
	private InsertExternalBudgetCommandHandler insert;

	/** The update. */
	@Inject
	private UpdateExternalBudgetCommandHandler update;

	/** The delete. */
	@Inject
	private DeleteExternalBudgetCommandHandler delete;
	
	/** The execute process handler. */
	@Inject
    private ExecutionProcessCommandHandler executeProcessHandler;
	
	/**
	 * Gets the all external budget.
	 *
	 * @return the all external budget
	 */
	@POST
	@Path("findallexternalbudget")
	public List<ExternalBudgetDto> getAllExternalBudget() {
		return this.find.findAll();
	}
	
	/**
	 * Insertbudget.
	 *
	 * @param command the command
	 */
	@POST
	@Path("insertexternalbudget")
	public void insertbudget(InsertExternalBudgetCommand command) {
		this.insert.handle(command);
	}

	/**
	 * Update data.
	 *
	 * @param command the command
	 */
	@POST
	@Path("updateexternalbudget")
	public void updateData(UpdateExternalBudgetCommand command) {
		this.update.handle(command);
	}

	/**
	 * Delete data.
	 *
	 * @param command the command
	 */
	@POST
	@Path("deleteexternalbudget")
	public void deleteData(DeleteExternalBudgetCommand command) {
		this.delete.handle(command);
	}

    /**
     * Find data preview.
     *
     * @param extractCondition the extract condition
     * @return the ext budget data preview dto
     */
    @POST
    @Path("find/preview")
    public ExtBudgetDataPreviewDto findDataPreview(ExtBudgetExtractCondition extractCondition) {
        return this.find.findDataPreview(extractCondition);
    }
    
    @POST
    @Path("import/validate/{fileId}")
    public void validateFile(@PathParam("fileId") String fileId) {
        this.find.validateFile(fileId);
    }
    
    /**
     * Execute import file.
     *
     * @param command the command
     */
    @POST
    @Path("import/execute")
    public JavaTypeResult<String> executeImportFile(ExecutionProcessCommand command) {
        return new JavaTypeResult<String>(this.executeProcessHandler.handle(command));
    }
    
    /**
     * Find completion list.
     *
     * @return the list
     */
    @POST
    @Path("find/completionenum")
    public List<EnumConstant> findCompletionList() {
        return EnumAdaptor.convertToValueNameList(CompletionState.class);
    }
    
    /**
     * Find all external budget log.
     *
     * @param query the query
     * @return the list
     */
    @POST
    @Path("findAll/log")
    public List<ExternalBudgetLogDto> findAllExternalBudgetLog(ExternalBudgetQuery query) {
        return this.find.findExternalBudgetLog(query);
    }
}
