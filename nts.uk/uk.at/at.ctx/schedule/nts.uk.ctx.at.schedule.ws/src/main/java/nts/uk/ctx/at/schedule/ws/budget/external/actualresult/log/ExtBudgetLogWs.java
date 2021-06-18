/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.budget.external.actualresult.log;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.export.budget.external.actualresult.ExtBudgetErrorExportService;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.log.ExtBudgetLogFinder;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.log.dto.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.log.dto.ExternalBudgetQuery;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.CompletionState;

/**
 * The Class ExtBudgetLogWs.
 */
@Path("at/schedule/budget/external/log")
@Produces(MediaType.APPLICATION_JSON)
public class ExtBudgetLogWs extends WebService {
    
    /** The log finder. */
    @Inject
    private ExtBudgetLogFinder logFinder;
    
    /** The export service. */
    @Inject
    private ExtBudgetErrorExportService exportService;
    
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
        return this.logFinder.findExternalBudgetLog(query);
    }
    
    /**
     * Export csv error.
     *
     * @param executeId the execute id
     * @return the export service result
     */
    @POST
    @Path("export/{executeId}")
    public ExportServiceResult exportCsvError(@PathParam("executeId") String executeId) {
        return this.exportService.start(executeId);
    }
}
