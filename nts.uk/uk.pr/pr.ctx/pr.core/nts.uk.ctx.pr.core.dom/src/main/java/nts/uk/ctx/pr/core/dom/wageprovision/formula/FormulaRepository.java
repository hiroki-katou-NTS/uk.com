package nts.uk.ctx.pr.core.dom.wageprovision.formula;

<<<<<<< HEAD
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.time.Year;
import java.util.Optional;
=======
>>>>>>> pj/pr/team_G/QMM019
import java.util.List;
import java.util.Optional;

/**
* 計算式
*/
public interface FormulaRepository
{

    List<Formula> getAllFormula();

<<<<<<< HEAD
    Optional<Formula> getFormulaById(String formulaCode);

    Optional<FormulaHistory> getFormulaHistoryByCode(String formulaCode);
=======
    Optional<Formula> getFormulaById(String cid, String formulaCode);

    List<Formula> getFormulaByCodes(String cid, List<String> formulaCodes);
>>>>>>> pj/pr/team_G/QMM019

    void add(Formula domain);

    void update(Formula domain);

    void remove(Formula domain);

    void insertFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonth);

    void updateFormulaHistory (String formulaCode, YearMonthHistoryItem yearMonth);

    void removeFormulaHistory (String historyID);

    void removeByFormulaCode (String formulaCode);

}
