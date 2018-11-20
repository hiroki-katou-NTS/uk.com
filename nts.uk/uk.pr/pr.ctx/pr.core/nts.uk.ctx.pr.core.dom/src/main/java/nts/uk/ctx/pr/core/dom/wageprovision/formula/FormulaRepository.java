package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.List;
import java.util.Optional;

/**
* 計算式
*/
public interface FormulaRepository
{

    List<Formula> getAllFormula();

    Optional<Formula> getFormulaById(String cid, String formulaCode);

    List<Formula> getFormulaByCodes(String cid, List<String> formulaCodes);

    void add(Formula domain);

    void update(Formula domain);

    void remove(String cid, String formulaCode);

}
