package nts.uk.ctx.pr.core.ws.wageprovision.statementlayout;


import nts.uk.ctx.pr.core.app.find.wageprovision.formula.FormulaDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.formula.FormulaFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutAndLastHistDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutHistFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableFinder;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementlayout")
@Produces("application/json")
public class StatementLayoutWebService {

    @Inject
    private SalIndAmountNameFinder salIndAmountNameFinder;

    @Inject
    private FormulaFinder formulaFinder;

    @Inject
    private WageTableFinder wageTableFinder;

    @Inject
    private StatementLayoutFinder statementLayoutFinder;
    @Inject
    private StatementLayoutHistFinder statementLayoutHistFinder;

    @POST
    @Path("getSalIndAmountName/{cateIndicator}")
    public List<SalIndAmountNameDto> getSalIndAmountName(@PathParam("cateIndicator") int cateIndicator) {
        return salIndAmountNameFinder.getAllSalIndAmountNameByCateIndi(cateIndicator);
    }

    @POST
    @Path("getFormulaByYearMonth/{yearMonth}")
    public List<FormulaDto> getFormulaByYearMonth(@PathParam("yearMonth") int yearMonth) {
        return formulaFinder.getFormulaByYearMonth(yearMonth);
    }

    @POST
    @Path("getWageTableByYearMonth/{yearMonth}")
    public List<WageTableDto> getWageTableByYearMonth(@PathParam("yearMonth") int yearMonth) {
        return wageTableFinder.getWageTableByYearMonth(yearMonth);
    }

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
