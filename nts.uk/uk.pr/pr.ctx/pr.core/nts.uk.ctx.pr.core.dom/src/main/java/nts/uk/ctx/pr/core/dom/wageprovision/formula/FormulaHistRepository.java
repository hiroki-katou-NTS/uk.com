package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.List;
import java.util.Optional;

public interface FormulaHistRepository {

    Optional<FormulaHist> getFormulaHistById(String cid, int formulaCode, String histId);

    List<FormulaHist> getAll(String cid);
}
