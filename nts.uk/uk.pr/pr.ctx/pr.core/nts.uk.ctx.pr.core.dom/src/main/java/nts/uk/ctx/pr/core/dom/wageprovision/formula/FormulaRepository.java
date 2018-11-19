package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.time.Year;
import java.util.Optional;
import java.util.List;

/**
* 計算式
*/
public interface FormulaRepository
{

    List<Formula> getAllFormula();

    Optional<Formula> getFormulaById(String formulaCode);

    Optional<FormulaHistory> getFormulaHistoryByCode(String formulaCode);

    void add(Formula domain);

    void update(Formula domain);

    void remove(Formula domain);

    void insertFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonth);

    void updateFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonth);

    void removeFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonth);

    void removeByFormulaCode (String formulaCode);

}
