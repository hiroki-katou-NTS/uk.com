package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;

/**
* 計算式
*/
public interface FormulaRepository
{

    List<Formula> getAllFormula();

    Optional<Formula> getFormulaById();

    void add(Formula domain);

    void update(Formula domain);

    void remove();

}
