package nts.uk.ctx.pr.core.ws.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout.*;
import nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.formula.FormulaDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.formula.FormulaFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.*;
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
    private AddStatementLayoutCommandHandler addStatementLayoutCommandHandler;

    @Inject
    private UpdateStatementLayoutHistCommandHandler updateStatementLayoutHistCommandHandler;

    @Inject
    private UpdateStatLayoutHistDataCommandHandler updateStatLayoutHistDataCommandHandler;

    @Inject
    private DeleteStatementLayoutHistCommandHandler deleteStatementLayoutHistCommandHandler;

    @POST
    @Path("getSalIndAmountName/{cateIndicator}")
    public List<SalIndAmountNameDto> getSalIndAmountName(@PathParam("cateIndicator") int cateIndicator) {
        return salIndAmountNameFinder.getAllSalIndAmountNameByCateIndi(cateIndicator);
    }

    @POST
    @Path("getSalIndAmountNameById/{individualPriceCode}/{cateIndicator}")
    public SalIndAmountNameDto getSalIndAmountNameById(@PathParam("individualPriceCode") String individualPriceCode,
                                                       @PathParam("cateIndicator") int cateIndicator) {
        return salIndAmountNameFinder.getSalIndAmountNameById(individualPriceCode, cateIndicator);
    }

    @POST
    @Path("getFormulaByYearMonth/{yearMonth}")
    public List<FormulaDto> getFormulaByYearMonth(@PathParam("yearMonth") int yearMonth) {
        return formulaFinder.getFormulaByYearMonth(yearMonth);
    }

    @POST
    @Path("getFormulaById/{formulaCode}")
    public FormulaDto getFormulaById(@PathParam("formulaCode") String formulaCode) {
        return formulaFinder.getFormulaById(formulaCode);
    }

    @POST
    @Path("getWageTableByYearMonth/{yearMonth}")
    public List<WageTableDto> getWageTableByYearMonth(@PathParam("yearMonth") int yearMonth) {
        return wageTableFinder.getWageTableByYearMonth(yearMonth);
    }

    @POST
    @Path("getWageTableById/{wageTableCode}")
    public WageTableDto getWageTableById(@PathParam("wageTableCode") String wageTableCode) {
        return wageTableFinder.getWageTableById(wageTableCode);
    }

    @POST
    @Path("getStatementItem")
    public List<StatementItemCustomDto> getStatementItem(StatementItemCustomDataDto dataDto) {
        return statementLayoutFinder.getStatementItem(dataDto);
    }

    @POST
    @Path("getPaymentItemStById/{categoryAtr}/{itemNameCode}")
    public PaymentItemSetDto getPaymentItemStById(@PathParam("categoryAtr") int categoryAtr,
                                                  @PathParam("itemNameCode") String itemNameCode) {
        return statementLayoutFinder.getPaymentItemStById(categoryAtr, itemNameCode);
    }

    @POST
    @Path("getDeductionItemStById/{categoryAtr}/{itemNameCode}")
    public DeductionItemSetDto getDeductionItemStById(@PathParam("categoryAtr") int categoryAtr,
                                                      @PathParam("itemNameCode") String itemNameCode) {
        return statementLayoutFinder.getDeductionItemStById(categoryAtr, itemNameCode);
    }

    @POST
    @Path("getAttendanceItemStById/{categoryAtr}/{itemNameCode}")
    public AttendanceItemSetDto getAttendanceItemStById(@PathParam("categoryAtr") int categoryAtr,
                                                        @PathParam("itemNameCode") String itemNameCode) {
        return statementLayoutFinder.getAttendanceItemStById(categoryAtr, itemNameCode);
    }

    @POST
    @Path("getCurrentProcessingDate")
    public Integer getCurrentProcessingDate() {
        return statementLayoutFinder.getCurrentProcessingDate();
    }

    @POST
    @Path("getStatementLayoutByProcessingDate/{processingDate}")
    public List<StatementLayoutDto> getStatementLayoutByProcessingDate(@PathParam("processingDate") int processingDate) {
        return statementLayoutFinder.getStatementLayoutByProcessingDate(processingDate);
    }

    @POST
    @Path("getAllStatementLayoutAndHist")
    public List<StatementLayoutAndHistDto> getAllStatementLayoutAndHist() {
        return this.statementLayoutFinder.getAllStatementLayoutAndHist();
    }

    @POST
    @Path("getAllStatementLayoutAndLastHist")
    public List<StatementLayoutAndHistDto> getAllStatementLayoutAndLastHist() {
        return this.statementLayoutFinder.getAllStatementLayoutAndLastHist();
    }

    @POST
    @Path("getStatementLayoutAndLastHist/{code}")
    public StatementLayoutAndHistDto getStatementLayoutAndLastHist(@PathParam("code") String code) {
        return this.statementLayoutFinder.getStatementLayoutAndLastHist(code);
    }

    @POST
    @Path("getHistByCidAndCodeAndAfterDate/{code}/{startYearMonth}")
    public List<YearMonthHistoryItemDto> getHistByCidAndCodeAndAfterDate(@PathParam("code") String code, @PathParam("startYearMonth") Integer startYearMonth) {
        return this.statementLayoutHistFinder.getHistByCidAndCodeAndAfterDate(code, startYearMonth);
    }

    @POST
    @Path("getStatementLayoutAndHistById/{code}/{histId}")
    public StatementLayoutAndHistDto getStatementLayoutAndHistById(@PathParam("code") String code, @PathParam("histId") String histId) {
        return this.statementLayoutHistFinder.getStatementLayoutAndHistById(code, histId);
    }

    @POST
    @Path("getStatementLayoutHistData/{code}/{histId}")
    public StatementLayoutHistDataDto getStatementLayoutHistData(@PathParam("code") String code, @PathParam("histId") String histId) {
        return this.statementLayoutHistFinder.getStatementLayoutHistData(code, histId).orElse(null);
    }

    @POST
    @Path("getInitStatementLayoutHistData/{statementCode}/{histId}/{startMonth}/{itemHistoryDivision}/{layoutPattern}")
    public StatementLayoutHistDataDto getInitStatementLayoutHistData(@PathParam("statementCode") String statementCode, @PathParam("histId") String histId, @PathParam("startMonth") Integer startMonth,
                                                                     @PathParam("itemHistoryDivision") Integer itemHistoryDivision, @PathParam("layoutPattern") Integer layoutPattern) {
        return this.statementLayoutHistFinder.getInitStatementLayoutHistData(statementCode, histId, startMonth, itemHistoryDivision, layoutPattern).orElse(null);
    }

    @POST
    @Path("getLastStatementLayoutHistData/{code}")
    public StatementLayoutHistDataDto getLastStatementLayoutHistData(@PathParam("code") String code) {
        return this.statementLayoutHistFinder.getLastStatementLayoutHistData(code).orElse(null);
    }

    @POST
    @Path("addStatementLayout")
    public void addStatementLayout(StatementLayoutCommand command) {
        this.addStatementLayoutCommandHandler.handle(command);
    }

    @POST
    @Path("updateStatementLayoutHistData")
    public void updateStatementLayoutHistData(StatementLayoutHistDataCommand command) {
        this.updateStatLayoutHistDataCommandHandler.handle(command);
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

    @POST
    @Path("getAllStatementLayoutHist/{startYearMonth}")
    public List<StatementNameLayoutHistDto> getAllStatementLayoutHist(@PathParam("startYearMonth") String startYearMonth) {
        List<StatementNameLayoutHistDto> stateUseUnitSettingDto = statementLayoutHistFinder.getAllStatementLayoutHist(Integer.valueOf(startYearMonth));
        return stateUseUnitSettingDto;
    }

}
