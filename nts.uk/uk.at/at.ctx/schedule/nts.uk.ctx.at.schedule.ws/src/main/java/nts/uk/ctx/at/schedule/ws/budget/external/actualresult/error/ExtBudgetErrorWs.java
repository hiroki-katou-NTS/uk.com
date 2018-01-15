/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.budget.external.actualresult.error;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.error.ExtBudgetErrorFinder;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.error.dto.ExternalBudgetErrorDto;

/**
 * The Class ExtBudgetErrorWs.
 */
@Path("at/schedule/budget/external/error")
@Produces(MediaType.APPLICATION_JSON)
public class ExtBudgetErrorWs extends WebService {

    /** The error finder. */
    @Inject
    private ExtBudgetErrorFinder errorFinder;
    
    /**
     * Find errors.
     *
     * @param executeId the execute id
     * @return the list
     */
    @POST
    @Path("find/{executeId}")
    public List<ExternalBudgetErrorDto> findErrors(@PathParam("executeId") String executeId) {
        return this.errorFinder.findErrors(executeId);
    }
}
