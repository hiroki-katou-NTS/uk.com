/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.budget.external;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.external.DeleteExternalBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.external.DeleteExternalBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.InsertExternalBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.external.InsertExternalBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.UpdateExternalBudgetCommand;
import nts.uk.ctx.at.schedule.app.command.budget.external.UpdateExternalBudgetCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.ExecutionProcessCommand;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.ExecutionProcessCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExecutionInfor;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetFinder;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExtBudgetDataPreviewDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExtBudgetExtractCondition;

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
	 * Checks if is daily unit.
	 *
	 * @param externalBudgetCd the external budget cd
	 * @return true, if is daily unit
	 */
	@POST
    @Path("validate/isDailyUnit")
    public boolean isDailyUnit(String externalBudgetCd) {
        return this.find.isDailyUnit(externalBudgetCd);
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
    @Path("import/validate")
    public void validateFile(ExtBudgetExtractCondition extractCondition) {
        this.find.validateFile(extractCondition);
    }
    
    /**
     * Execute import file.
     *
     * @param command the command
     */
    @POST
    @Path("import/execute")
    public ExecutionInfor executeImportFile(ExecutionProcessCommand command) {
        return this.executeProcessHandler.handle(command);
    }
}
