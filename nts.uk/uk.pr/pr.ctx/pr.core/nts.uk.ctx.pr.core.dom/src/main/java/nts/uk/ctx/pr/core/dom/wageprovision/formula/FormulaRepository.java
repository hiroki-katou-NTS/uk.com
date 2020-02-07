package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.Optional;

/**
* 計算式
*/
public interface FormulaRepository {

    List<Formula> getAllFormula();

    Optional<Formula> getFormulaById(String formulaCode);

    Optional<FormulaHistory> getFormulaHistoryByCode(String formulaCode);

    List<Formula> getFormulaByCodes(String cid, List<String> formulaCodes);

    Map<String, String> getFormulaWithUsableDetailSetting();

    void add(Formula domain);

    void update(Formula domain);

    void remove(Formula domain);

    void insertFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonth);

    void updateFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonth);

    int removeFormulaHistory (String historyID);

    void removeByFormulaCode (String formulaCode);

    List<FormulaHistory> getFormulaHistByYearMonth(YearMonth yearMonth);

}
