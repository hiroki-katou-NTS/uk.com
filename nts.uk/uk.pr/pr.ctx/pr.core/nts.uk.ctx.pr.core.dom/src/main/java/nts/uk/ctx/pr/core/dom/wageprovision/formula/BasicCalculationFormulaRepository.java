package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;

/**
* かんたん計算式
*/
public interface BasicCalculationFormulaRepository
{

    List<BasicCalculationFormula> getAllBasicCalculationFormula();

    Optional<BasicCalculationFormula> getBasicCalculationFormulaById();

    void add(BasicCalculationFormula domain);

    void update(BasicCalculationFormula domain);

    void remove();

}
