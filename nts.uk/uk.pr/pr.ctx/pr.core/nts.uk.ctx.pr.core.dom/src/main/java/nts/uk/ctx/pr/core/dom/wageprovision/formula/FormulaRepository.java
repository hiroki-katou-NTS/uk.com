package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;

/**
* 計算式
*/
public interface FormulaRepository
{

    List<Formula> getAllFormula();

    Optional<Formula> getFormulaById(String cid, int formulaCode);

    void add(Formula domain);

    void update(Formula domain);

    void remove(String cid, String formulaCode);

}
