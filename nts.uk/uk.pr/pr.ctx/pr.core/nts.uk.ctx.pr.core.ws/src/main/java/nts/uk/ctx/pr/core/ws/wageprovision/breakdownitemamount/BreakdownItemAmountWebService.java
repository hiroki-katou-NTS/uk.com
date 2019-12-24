package nts.uk.ctx.pr.core.ws.wageprovision.breakdownitemamount;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount.AddBreakdownAmountHisCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount.BreakdownAmountHisCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount.RemoveBreakdownAmountHisCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount.UpdateBreakdownAmountHisCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount.BreakdownAmountHisDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount.BreakdownAmountHisFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount.BreakdownAmountListDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount.StatementDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @author thanh.tq
 */
@Path("ctx/pr/core/breakdownAmountHis")
@Produces("application/json")
public class BreakdownItemAmountWebService extends WebService {

    @Inject
    private BreakdownAmountHisFinder breakdownAmountHisFinder;

    @Inject
    private AddBreakdownAmountHisCommandHandler addBreakdownAmountHisCommandHandler;

    @Inject
    private UpdateBreakdownAmountHisCommandHandler updateBreakdownAmountHisCommandHandler;

    @Inject
    private RemoveBreakdownAmountHisCommandHandler removeBreakdownAmountHisCommandHandler;


    @POST
    @Path("getStatemetItem")
    public List<StatementDto> getStatemetItem() {
        return this.breakdownAmountHisFinder.getStatemetItem();
    }

    @POST
    @Path("getBreakdownHis/{categoryAtr}/{itemNameCd}/{salaryBonusAtr}/{employeeID}")
    public BreakdownAmountHisDto getBreakdownHis(@PathParam("categoryAtr") int categoryAtr,
                                                 @PathParam("itemNameCd") String itemNameCd,
                                                 @PathParam("salaryBonusAtr") int salaryBonusAtr,
                                                 @PathParam("employeeID") String employeeID) {
        return this.breakdownAmountHisFinder.getBreakdownHis(categoryAtr, itemNameCd, salaryBonusAtr, employeeID);
    }

    @POST
    @Path("getBreakDownAmoun/{historyID}/{categoryAtr}/{itemNameCd}")
    public List<BreakdownAmountListDto> getBreakDownAmoun(@PathParam("historyID") String historyID,
                                                          @PathParam("categoryAtr") int categoryAtr,
                                                          @PathParam("itemNameCd") String itemNameCd) {
        return this.breakdownAmountHisFinder.getBreakDownAmoun(historyID, categoryAtr, itemNameCd);
    }

    @POST
    @Path("addBreakdownAmountHis")
    public void addBreakdownAmountHis(BreakdownAmountHisCommand command) {
        this.addBreakdownAmountHisCommandHandler.handle(command);
    }

    @POST
    @Path("updateBreakdownAmountHis")
    public void updateBreakdownAmountHis(BreakdownAmountHisCommand command) {
        this.updateBreakdownAmountHisCommandHandler.handle(command);
    }

    @POST
    @Path("removeBreakdownAmountHis")
    public void removeBreakdownAmountHis(BreakdownAmountHisCommand command) {
        this.removeBreakdownAmountHisCommandHandler.handle(command);
    }
}
