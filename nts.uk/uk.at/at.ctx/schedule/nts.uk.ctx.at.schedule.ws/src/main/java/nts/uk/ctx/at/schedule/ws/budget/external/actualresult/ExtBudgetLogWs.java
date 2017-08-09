package nts.uk.ctx.at.schedule.ws.budget.external.actualresult;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetLogFinder;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExternalBudgetQuery;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.CompletionState;

@Path("at/schedule/budget/external/log")
@Produces(MediaType.APPLICATION_JSON)
public class ExtBudgetLogWs extends WebService {
    
    @Inject
    private ExtBudgetLogFinder logFinder;
    
    
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
}
