package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHist;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHistRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtFormulaHist;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaCalcFormulaHistRepository extends JpaRepository implements FormulaHistRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtFormulaHist f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.formulaHistPk.cid =:cid AND" +
            " f.formulaHistPk.formulaCode =:formulaCode" +
            " f.formulaHistPk.histId =:histId";

    @Override
    public Optional<FormulaHist> getFormulaHistById(String cid, int formulaCode, String histId) {
        return null;
        /*return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtFormulaHist.class)
                .setParameter("cid", cid)
                .setParameter("formulaCode", formulaCode)
                .getSingle(c->c.toDomain());*/
    }

    @Override
    public List<FormulaHist> getAll(String cid) {
        return null;
    }
}
