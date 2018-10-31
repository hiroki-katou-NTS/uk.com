package nts.uk.ctx.pr.core.ws.wageprovision.statementlayout;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutAndLastHistDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutHistFinder;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("ctx/pr/core/wageprovision/statementlayout")
@Produces("application/json")
public class StatementLayoutWebService extends WebService {
    @Inject
    private StatementLayoutFinder statementLayoutFinder;
    @Inject
    private StatementLayoutHistFinder statementLayoutHistFinder;

    @POST
    @Path("getStatementLayoutAndLastHist/{code}")
    public StatementLayoutAndLastHistDto getStatementLayoutAndLastHist(@PathParam("code") String code) {
        return this.statementLayoutFinder.getStatementLayoutAndLastHist(code);
    }

    @POST
    @Path("getStatementLayoutAndLastHist/{code}/{startYearMonth}")
    public YearMonthHistoryItem getStatementLayoutAndLastHist(@PathParam("code") String code, @PathParam("startYearMonth") Integer startYearMonth) {
        return this.statementLayoutHistFinder.getHistByCidAndCodeAndAfterDate(code, startYearMonth).orElse(null);
    }
}
