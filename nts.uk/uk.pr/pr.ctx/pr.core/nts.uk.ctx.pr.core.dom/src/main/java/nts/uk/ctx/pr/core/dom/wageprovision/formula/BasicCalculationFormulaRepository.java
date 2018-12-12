package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;

/**
* かんたん計算式
*/
public interface BasicCalculationFormulaRepository {

    List<BasicCalculationFormula> getBasicCalculationFormulaByHistoryID(String historyID);

    void addAll(List<BasicCalculationFormula> domains);

    void upsertAll(String historyID, List<BasicCalculationFormula> domains);

    void updateAll(List<BasicCalculationFormula> domains);

    void removeAll(List<BasicCalculationFormula> domains);

    void removeByHistory(String historyID);

    void removeByFormulaCode(String formulaCode);

}
