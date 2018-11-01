package nts.uk.ctx.pr.core.ws.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout.DeleteStatementLayoutHistCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout.StatementLayoutHistCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout.UpdateStatementLayoutHistCommandHandler;
import nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutAndHistDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutHistFinder;

import nts.uk.ctx.pr.core.app.find.wageprovision.formula.FormulaDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.formula.FormulaFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableFinder;

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

    @Inject
    private UpdateStatementLayoutHistCommandHandler updateStatementLayoutHistCommandHandler;

    @Inject
    private DeleteStatementLayoutHistCommandHandler deleteStatementLayoutHistCommandHandler;

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
    public StatementLayoutAndHistDto getStatementLayoutAndLastHist(@PathParam("code") String code) {
        return this.statementLayoutFinder.getStatementLayoutAndLastHist(code);
    }

    @POST
    @Path("getStatementLayoutAndLastHist/{code}/{startYearMonth}")
    public List<YearMonthHistoryItemDto> getStatementLayoutAndLastHist(@PathParam("code") String code, @PathParam("startYearMonth") Integer startYearMonth) {
        return this.statementLayoutHistFinder.getHistByCidAndCodeAndAfterDate(code, startYearMonth);
    }

    @POST
    @Path("updateStatementLayoutHist")
    public void updateStatementLayoutHist(StatementLayoutHistCommand command) {
        this.updateStatementLayoutHistCommandHandler.handle(command);
    }

    @POST
    @Path("deleteStatementLayoutHist")
    public void deleteStatementLayoutHist(StatementLayoutHistCommand command) {
        this.deleteStatementLayoutHistCommandHandler.handle(command);
    }
}
