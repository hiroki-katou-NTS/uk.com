package nts.uk.ctx.pr.core.ws.wageprovision.statementlayout;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutAndHistDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutHistFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/pr/core/wageprovision/statementlayout")
@Produces("application/json")
public class StatementLayoutWebService extends WebService {
    @Inject
    private StatementLayoutFinder statementLayoutFinder;
    @Inject
    private StatementLayoutHistFinder statementLayoutHistFinder;

    @POST
    @Path("getStatementLayoutAndLastHist/{code}")
    public StatementLayoutAndHistDto getStatementLayoutAndLastHist(@PathParam("code") String code) {
        return this.statementLayoutFinder.getStatementLayoutAndLastHist(code);
    }

    @POST
    @Path("getStatementLayoutAndLastHist/{code}/{startYearMonth}")
    public List<YearMonthHistoryItemDto> getStatementLayoutAndLastHist(@PathParam("code") String code, @PathParam("startYearMonth") Integer startYearMonth) {
        return this.statementLayoutHistFinder.getHistByCidAndCodeAndAfterDate(code, startYearMonth);
    }
}
