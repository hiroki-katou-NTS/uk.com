package nts.uk.ctx.pr.core.app.find.wageprovision.formula;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount.PayrollUnitPriceHistoryFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemDataFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename.SalaryPerUnitPriceFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename.SalaryPerUnitPriceNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaElementDto;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.MasterUseDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 計算式: Finder
*/
public class FormulaFinder {

    @Inject
    private FormulaRepository formulaRepository;

    @Inject
    private FormulaService formulaService;

    @Inject
    private StatementItemDataFinder statementItemDataFinder;

    @Inject
    private PayrollUnitPriceFinder payrollUnitPriceFinder;

    @Inject
    private SalaryPerUnitPriceFinder salaryPerUnitPriceFinder;

    @Inject
    private WageTableRepository wageTableRepository;

    public List<FormulaDto> getAllFormula() {
        return formulaRepository.getAllFormula().stream().map(FormulaDto::fromDomainToDto).collect(Collectors.toList());
    }

    public List<FormulaDto> getAllFormulaAndHistory() {
        return formulaRepository.getAllFormula().stream().map(item -> {
            FormulaDto dto = FormulaDto.fromDomainToDto(item);
            formulaRepository.getFormulaHistoryByCode(item.getFormulaCode().v()).ifPresent(formulaHistory -> {
                dto.setFormulaHistory(formulaHistory.getHistory());
            });
            return dto;
        }).collect(Collectors.toList());
    }
    public List<FormulaDto> getFormulaByYearMonth(int yearMonth) {
        return formulaService.getFormulaByYearMonth(new YearMonth(yearMonth))
                .stream().map(FormulaDto::fromDomainToDto).collect(Collectors.toList());
    }

    public FormulaDto getFormulaById(String formulaCode) {
        return formulaRepository.getFormulaById(formulaCode).map(FormulaDto::fromDomainToDto).orElse(null);
    }

    public List<MasterUseDto> getMasterUseInfo (int masterUseClassification) {
        return formulaService.getMasterUseInfo(masterUseClassification);
    }

    public Map<String, List<FormulaElementDto>> getFormulaElement(int yearMonth) {
        Map<String, List<FormulaElementDto>> formulaElements = new HashMap<String, List<FormulaElementDto>>();
        formulaElements.put("paymentItem", statementItemDataFinder.getAllStatementItemData(CategoryAtr.PAYMENT_ITEM.value, false).stream().map(item -> new FormulaElementDto(item.getItemNameCd(), item.getName())).collect(Collectors.toList()));
        formulaElements.put("deductionItem", statementItemDataFinder.getAllStatementItemData(CategoryAtr.DEDUCTION_ITEM.value, false).stream().map(item -> new FormulaElementDto(item.getItemNameCd(), item.getName())).collect(Collectors.toList()));
        formulaElements.put("attendanceItem", statementItemDataFinder.getAllStatementItemData(CategoryAtr.ATTEND_ITEM.value, false).stream().map(item -> new FormulaElementDto(item.getItemNameCd(), item.getName())).collect(Collectors.toList()));
        formulaElements.put("companyUnitPriceItem", payrollUnitPriceFinder.getPayrollUnitPriceByYearMonth(yearMonth).stream().map(item -> new FormulaElementDto(item.getCode(), item.getName())).collect(Collectors.toList()));
        formulaElements.put("individualUnitPriceItem", this.salaryPerUnitPriceFinder.getAllSalaryPerUnitPriceName().stream().filter(item -> item.getAbolition() == 0).map(item -> new FormulaElementDto(item.getCode(), item.getName())).collect(Collectors.toList()));
        formulaElements.put("wageTableItem", wageTableRepository.getAllWageTable(AppContexts.user().companyId()).stream().map(item -> new FormulaElementDto(item.getWageTableCode().v(), item.getWageTableName().v(), item.getRemarkInformation().map(PrimitiveValueBase::v).orElse(null))).collect(Collectors.toList()));
        return formulaElements;
    }

    public Map<String, String> getProcessYearMonthAndReferenceTime () {
        return formulaService.getProcessYearMonthAndReferenceTime();
    }
}

