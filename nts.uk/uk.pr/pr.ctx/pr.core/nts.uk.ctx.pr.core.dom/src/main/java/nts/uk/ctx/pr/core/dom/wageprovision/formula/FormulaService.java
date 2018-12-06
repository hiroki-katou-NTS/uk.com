package nts.uk.ctx.pr.core.dom.wageprovision.formula;
import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
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

    public void addFormula (Formula formula, BasicFormulaSetting basicFormulaSetting, YearMonthHistoryItem yearMonthHistoryItem) {
        if (formulaRepository.getFormulaById(formula.getFormulaCode().v()).isPresent()) throw new BusinessException("Msg_3");
        formulaRepository.add(formula);
        formulaRepository.insertFormulaHistory(formula.getFormulaCode().v(), yearMonthHistoryItem);
        if (formula.getSettingMethod() == FormulaSettingMethod.BASIC_SETTING) basicFormulaSettingRepository.add(basicFormulaSetting);
    }

    public void removeFormula (String formulaCode) {
        formulaRepository.removeByFormulaCode(formulaCode);
        detailFormulaSettingRepository.removeByFormulaCode(formulaCode);
        basicCalculationFormulaRepository.removeByFormulaCode(formulaCode);
        basicFormulaSettingRepository.removeByFormulaCode(formulaCode);
    }

    public void addFormulaSetting (Formula formula, BasicFormulaSetting basicFormulaSetting, DetailFormulaSetting detailFormulaSetting, List<BasicCalculationFormula> basicCalculationFormula) {
        detailFormulaSettingRepository.add(detailFormulaSetting);
        basicCalculationFormulaRepository.addAll(basicCalculationFormula);
        basicFormulaSettingRepository.add(basicFormulaSetting);
    }

    public void updateFormulaSetting (Formula formula, BasicFormulaSetting basicFormulaSetting, DetailFormulaSetting detailFormulaSetting, List<BasicCalculationFormula> basicCalculationFormula) {
        detailFormulaSettingRepository.update(detailFormulaSetting);
        basicCalculationFormulaRepository.updateAll(basicCalculationFormula);
        basicFormulaSettingRepository.update(basicFormulaSetting);
    }
    public void addFormulaHistory (Formula formula, BasicFormulaSetting basicFormulaSetting, DetailFormulaSetting detailFormulaSetting, List<BasicCalculationFormula> basicCalculationFormula, YearMonthHistoryItem yearMonthHistoryItem) {
        String formulaCode = formula.getFormulaCode().v();
        formulaRepository.getFormulaHistoryByCode(formulaCode).ifPresent(formulaHistory -> {
            if (formulaHistory.getHistory().size() > 0) {
                YearMonthHistoryItem lastHistory = formulaHistory.getHistory().get(0);
                lastHistory.changeSpan(new YearMonthPeriod(lastHistory.start(), yearMonthHistoryItem.start().addMonths(-1)));
                formulaRepository.updateFormulaHistory(formulaCode, lastHistory);
            }
        });
        this.addFormulaSetting(formula, basicFormulaSetting, detailFormulaSetting, basicCalculationFormula);
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
            formulaRepository.removeFormulaHistory(yearMonthDelete.identifier());
            try {
                YearMonthHistoryItem beforeYearMonth = yearMonthHistoryItems.get(currentIndex + 1);
                beforeYearMonth.changeSpan(new YearMonthPeriod(beforeYearMonth.start(), new YearMonth(LAST_YM_VALUE)));
                formulaRepository.updateFormulaHistory(formulaCode, beforeYearMonth);
            } catch (IndexOutOfBoundsException e) {
                return;
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

}
