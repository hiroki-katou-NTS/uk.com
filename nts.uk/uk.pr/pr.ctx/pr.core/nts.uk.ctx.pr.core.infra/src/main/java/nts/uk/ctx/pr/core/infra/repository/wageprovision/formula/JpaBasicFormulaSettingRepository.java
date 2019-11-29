package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import java.util.Collections;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicFormulaSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicFormulaSettingRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtBasicFormulaSetting;

@Stateless
public class JpaBasicFormulaSettingRepository extends JpaRepository implements BasicFormulaSettingRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBasicFormulaSetting f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.basicFormulaSetPk.historyID =:historyID ";

    private static final String REMOVE_BY_HISTORY = "DELETE FROM QpbmtBasicFormulaSetting f WHERE f.basicFormulaSetPk.historyID =:historyID ";
    private static final String REMOVE_BY_FORMULA_CODE = "DELETE FROM QpbmtBasicFormulaSetting f WHERE f.basicFormulaSetPk.formulaCode =:formulaCode ";


    @Override
    public Optional<BasicFormulaSetting> getBasicFormulaSettingById(String historyID){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtBasicFormulaSetting.class)
        .setParameter("historyID", historyID)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(BasicFormulaSetting domain){
        this.commandProxy().insert(QpbmtBasicFormulaSetting.toEntity(domain));
    }

    @Override
    public void upsert(BasicFormulaSetting domain){
        this.removeByHistory(domain.getHistoryID());
        this.commandProxy().insert(QpbmtBasicFormulaSetting.toEntity(domain));
    }

    @Override
    public void removeByHistory(String historyID) {
        this.getEntityManager().createQuery(REMOVE_BY_HISTORY).setParameter("historyID", historyID).executeUpdate();
    }
}
