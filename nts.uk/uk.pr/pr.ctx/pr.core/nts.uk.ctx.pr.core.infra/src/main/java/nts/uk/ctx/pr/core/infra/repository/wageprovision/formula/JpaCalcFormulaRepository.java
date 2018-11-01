package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.Formula;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtFormula;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtFormulaPk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaCalcFormulaRepository extends JpaRepository implements FormulaRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtFormula f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.formulaPk.cid =:cid AND" +
            " f.formulaPk.formulaCode =:formulaCode ";
    private static final String SELECT_BY_FORMULA_CDS = SELECT_ALL_QUERY_STRING + " WHERE  f.formulaPk.cid =:cid AND" +
            " f.formulaPk.formulaCode IN :formulaCodes" +
            " ORDER BY f.formulaPk.formulaCode ASC";

    @Override
    public List<Formula> getAllFormula() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtFormula.class)
                .getList(QpbmtFormula::toDomain);
    }

    @Override
    public Optional<Formula> getFormulaById(String cid, String formulaCode) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtFormula.class)
                .setParameter("cid", cid)
                .setParameter("formulaCode", formulaCode)
                .getSingle(QpbmtFormula::toDomain);
    }

    @Override
    public List<Formula> getFormulaByCodes(String cid, List<String> formulaCodes) {
        if (formulaCodes == null || formulaCodes.isEmpty()) return Collections.emptyList();
        return this.queryProxy().query(SELECT_BY_FORMULA_CDS, QpbmtFormula.class)
                .setParameter("cid", cid)
                .setParameter("formulaCodes", formulaCodes)
                .getList(QpbmtFormula::toDomain);
    }

    @Override
    public void add(Formula domain) {
        this.commandProxy().insert(QpbmtFormula.toEntity(domain));
    }

    @Override
    public void update(Formula domain) {
        this.commandProxy().update(QpbmtFormula.toEntity(domain));
    }

    @Override
    public void remove(String cid, String formulaCode) {
        this.commandProxy().remove(QpbmtFormula.class, new QpbmtFormulaPk(cid, formulaCode));
    }
}
