package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHist;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHistRepository;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaCalcFormulaHistRepository implements FormulaHistRepository {
    @Override
    public List<FormulaHist> getAll(String cid) {
        return null;
    }
}
