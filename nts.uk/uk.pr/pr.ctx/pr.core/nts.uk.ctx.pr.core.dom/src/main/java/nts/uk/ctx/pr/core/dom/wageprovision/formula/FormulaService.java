package nts.uk.ctx.pr.core.dom.wageprovision.formula;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.SysClassificationAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.SysDepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class FormulaService {

    @Inject
    private FormulaRepository formulaRepository;

    @Inject
    private BasicFormulaSettingRepository basicFormulaSettingRepository;

    @Inject
    private DetailFormulaSettingRepository detailFormulaSettingRepository;

    @Inject
    private BasicCalculationFormulaRepository basicCalculationFormulaRepository;

    @Inject
    private SysEmploymentAdapter sysEmploymentAdapter;

    @Inject
    private SyJobTitleAdapter syJobTitleAdapter;

    @Inject
    private SysDepartmentAdapter sysDepartmentAdapter;

    @Inject
    private SysClassificationAdapter sysClassificationAdapter;

    @Inject
    private SalaryClassificationInformationRepository salaryClassificationInformationRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;

    @Inject
    private SetDaySupportRepository setDaySupportRepository;

    public void addFormula (Formula formula, BasicFormulaSetting basicFormulaSetting, YearMonthHistoryItem yearMonthHistoryItem) {
        if (formulaRepository.getFormulaById(formula.getFormulaCode().v()).isPresent()) throw new BusinessException("Msg_3");
        formulaRepository.add(formula);
        formulaRepository.insertFormulaHistory(formula.getFormulaCode().v(), yearMonthHistoryItem);
        if (formula.getSettingMethod() == FormulaSettingMethod.BASIC_SETTING) basicFormulaSettingRepository.add(basicFormulaSetting);
    }

    public void updateFormula (Formula formula) {
        formulaRepository.update(formula);
    }

    public void updateFormulaSetting (Formula formula, BasicFormulaSetting basicFormulaSetting, DetailFormulaSetting detailFormulaSetting, List<BasicCalculationFormula> basicCalculationFormula) {
        formulaRepository.update(formula);
        basicCalculationFormulaRepository.upsertAll(basicFormulaSetting.getHistoryID(), basicCalculationFormula);
        if (formula.getSettingMethod() == FormulaSettingMethod.DETAIL_SETTING) detailFormulaSettingRepository.upsert(detailFormulaSetting);
        else basicFormulaSettingRepository.upsert(basicFormulaSetting);
    }
    public void addFormulaHistory (Formula formula, BasicFormulaSetting basicFormulaSetting, DetailFormulaSetting detailFormulaSetting, List<BasicCalculationFormula> basicCalculationFormula, YearMonthHistoryItem yearMonthHistoryItem) {
        String formulaCode = formula.getFormulaCode().v();
        formulaRepository.insertFormulaHistory(formulaCode, yearMonthHistoryItem);
        formulaRepository.getFormulaHistoryByCode(formulaCode).ifPresent(formulaHistory -> {
            if (formulaHistory.getHistory().size() > 1) {
                YearMonthHistoryItem lastHistory = formulaHistory.getHistory().get(1);
                lastHistory.changeSpan(new YearMonthPeriod(lastHistory.start(), yearMonthHistoryItem.start().addMonths(-1)));
                formulaRepository.updateFormulaHistory(formulaCode, lastHistory);
            }
        });
        this.updateFormulaSetting(formula, basicFormulaSetting, detailFormulaSetting, basicCalculationFormula);
    }
    public void updateFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonthUpdate) {
        formulaRepository.getFormulaHistoryByCode(formulaCode).ifPresent(formulaHistory -> {
            List<YearMonthHistoryItem> yearMonthHistoryItems = formulaHistory.getHistory();
            int currentIndex = yearMonthHistoryItems.indexOf(yearMonthUpdate);
            formulaRepository.updateFormulaHistory(formulaCode, yearMonthUpdate);
            try {
                YearMonthHistoryItem beforeYearMonth = yearMonthHistoryItems.get(currentIndex+1);
                beforeYearMonth.changeSpan(new YearMonthPeriod(beforeYearMonth.start(), yearMonthUpdate.start().addMonths(-1)));
                formulaRepository.updateFormulaHistory(formulaCode, beforeYearMonth);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        });
    }
    public void removeFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonthDelete) {
        final int LAST_YM_VALUE = 999912;
        basicFormulaSettingRepository.removeByHistory(yearMonthDelete.identifier());
        basicCalculationFormulaRepository.removeByHistory(yearMonthDelete.identifier());
        detailFormulaSettingRepository.removeByHistory(yearMonthDelete.identifier());
        formulaRepository.getFormulaHistoryByCode(formulaCode).ifPresent(formulaHistory -> {
            List<YearMonthHistoryItem> yearMonthHistoryItems = formulaHistory.getHistory();
            int currentIndex = yearMonthHistoryItems.indexOf(yearMonthDelete);
            int removed = formulaRepository.removeFormulaHistory(yearMonthDelete.identifier());
            if (removed < 1) {
                throw new BusinessException("Delete failed!");
            }
            try {
                YearMonthHistoryItem beforeYearMonth = yearMonthHistoryItems.get(currentIndex + 1);
                beforeYearMonth.changeSpan(new YearMonthPeriod(beforeYearMonth.start(), new YearMonth(LAST_YM_VALUE)));
                formulaRepository.updateFormulaHistory(formulaCode, beforeYearMonth);
            } catch (IndexOutOfBoundsException e) {
                formulaRepository.removeByFormulaCode(formulaCode);
            }
        });
    }

    public List<Formula> getFormulaByYearMonth(YearMonth yearMonth) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「計算式履歴」を取得する
        List<String> formulaCodes = formulaRepository.getFormulaHistByYearMonth(yearMonth)
                .stream().map(x -> x.getFormulaCode().v()).collect(Collectors.toList());
        // ドメインモデル「計算式」を取得する
        return formulaRepository.getFormulaByCodes(cid, formulaCodes);
    }

    public Map<String, String> getProcessYearMonthAndWorkingDayNumber() {
        Map<String, String> processYMAndWorkingDayNumber = new HashMap<>();
        processYMAndWorkingDayNumber.put("processYearMonth", GeneralDate.today().toString("YYYY/MM"));
        processYMAndWorkingDayNumber.put("workingDayNumber", "0");
        currProcessDateRepository.getCurrProcessDateByIdAndProcessCateNo(AppContexts.user().companyId(), 1).ifPresent(processDate -> {
            processYMAndWorkingDayNumber.put("processYearMonth", processDate.getGiveCurrTreatYear().year() + "/" + String.format("%02d", processDate.getGiveCurrTreatYear().month()));
            setDaySupportRepository.getSetDaySupportByIdAndProcessDate(AppContexts.user().companyId(), 1, processDate.getGiveCurrTreatYear().v())
                    .ifPresent(setDaySupport -> processYMAndWorkingDayNumber.put("workingDayNumber", setDaySupport.getNumberWorkDay().toString()));
        });
        return processYMAndWorkingDayNumber;
    }

    public GeneralDate getReferenceDate() {
        /*【条件】
        会社ID＝ログイン会社
        処理日区分NO＝1 */
        GeneralDate [] generalDate = {GeneralDate.today()};
        currProcessDateRepository.getCurrProcessDateByIdAndProcessCateNo(AppContexts.user().companyId(), 1).ifPresent(processDate -> {
            setDaySupportRepository.getSetDaySupportByIdAndProcessDate(AppContexts.user().companyId(), 1, processDate.getGiveCurrTreatYear().v()).ifPresent(setDaySupport -> {
                generalDate[0] = setDaySupport.getEmpExtraRefeDate();
            });
        });
        return generalDate[0];
    }

    public List<MasterUseDto> getMasterUseInfo (int masterUseClassification) {
        MasterUse masterUse = EnumAdaptor.valueOf(masterUseClassification, MasterUse.class);
        GeneralDate baseDate = this.getReferenceDate();
        switch (masterUse) {
            case EMPLOYMENT: {
                return sysEmploymentAdapter.findAll(AppContexts.user().companyId()).stream().map(
                        item -> new MasterUseDto(item.getCode(), item.getName())
                ).collect(Collectors.toList());
            }
            case DEPARTMENT: {
                return sysDepartmentAdapter.getDepartmentByCompanyIdAndBaseDate(AppContexts.user().companyId(), baseDate).stream().map(item -> new MasterUseDto(item.getDepartmentCode(), item.getDepartmentName())).collect(Collectors.toList());
            }
            case CLASSIFICATION: {
                return sysClassificationAdapter.getClassificationByCompanyId(AppContexts.user().companyId()).stream().map(item -> new MasterUseDto(item.getClassificationCode(), item.getClassificationName())).collect(Collectors.toList());
            }
            case JOB_TITLE: {
                return syJobTitleAdapter.findAll(AppContexts.user().companyId(), baseDate).stream().map(item ->
                     new MasterUseDto(item.getJobTitleCode(), item.getJobTitleName())
                ).collect(Collectors.toList());
            }
            case SALARY_CLASSIFICATION: {
                return salaryClassificationInformationRepository.getAllSalaryClassificationInformation(AppContexts.user().companyId()).stream().map(item ->
                     new MasterUseDto(item.getSalaryClassificationCode().v(), item.getSalaryClassificationName().v())
                ).collect(Collectors.toList());
            }
            case SALARY_FORM: {
                final String FIXED_PREFIX = "000000000", FIRST_LINE = "月給", SECOND_LINE = "日給月給", THIRD_LINE = "日給", FOURTH_LINE = "時給";
                ArrayList<MasterUseDto> masterUseList = new ArrayList<>();
                masterUseList.add(new MasterUseDto(FIXED_PREFIX + 1, FIRST_LINE));
                masterUseList.add(new MasterUseDto(FIXED_PREFIX + 2, SECOND_LINE));
                masterUseList.add(new MasterUseDto(FIXED_PREFIX + 3, THIRD_LINE));
                masterUseList.add(new MasterUseDto(FIXED_PREFIX + 4, FOURTH_LINE));
                return masterUseList;
            }
        }
        return Collections.emptyList();
    }
}
