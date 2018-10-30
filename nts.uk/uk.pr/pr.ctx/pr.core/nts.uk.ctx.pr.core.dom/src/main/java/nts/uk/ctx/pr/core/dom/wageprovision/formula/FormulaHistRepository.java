package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.List;

public interface FormulaHistRepository {
    List<FormulaHist> getAll(String cid);
}
