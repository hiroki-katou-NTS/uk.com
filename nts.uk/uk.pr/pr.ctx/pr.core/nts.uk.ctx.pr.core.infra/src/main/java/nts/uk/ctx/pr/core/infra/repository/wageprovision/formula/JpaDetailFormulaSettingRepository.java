package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSettingRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtDetailFormulaSetting;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtDetailFormulaSettingPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class JpaDetailFormulaSettingRepository extends JpaRepository implements DetailFormulaSettingRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtDetailFormulaSetting f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.detailFormulaSetPk.historyID =:historyID ";

    private static final String REMOVE_BY_HISTORY = "DELETE FROM QpbmtDetailFormulaSetting f WHERE f.detailFormulaSetPk.historyID =:historyID ";
    private static final String REMOVE_BY_FORMULA_CODE = "DELETE FROM QpbmtDetailFormulaSetting f WHERE f.detailFormulaSetPk.formulaCode =:formulaCode ";

    @Override
    public List<DetailFormulaSetting> getAllDetailFormulaSetting(){
        return Collections.emptyList();
    }

    @Override
    public Optional<DetailFormulaSetting> getDetailFormulaSettingById(String historyID){
        return QpbmtDetailFormulaSetting.toDomain(this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtDetailFormulaSetting.class)
        .setParameter("historyID", historyID).getList());
    }

    @Override
    public void add(DetailFormulaSetting domain){
        this.commandProxy().insertAll(QpbmtDetailFormulaSetting.toEntity(domain));
    }

    @Override
    public void update(DetailFormulaSetting domain){
        this.commandProxy().updateAll(QpbmtDetailFormulaSetting.toEntity(domain));
    }

    @Override
    public void remove(DetailFormulaSetting domain){
        this.commandProxy().removeAll(QpbmtDetailFormulaSetting.toEntity(domain));
    }

    @Override
    public void removeByHistory(String historyID) {
        this.getEntityManager().createQuery(REMOVE_BY_HISTORY).setParameter("historyID", historyID).executeUpdate();
    }

    @Override
    public void removeByFormulaCode(String formulaCode) {
        this.getEntityManager().createQuery(REMOVE_BY_FORMULA_CODE).setParameter("formulaCode", formulaCode).executeUpdate();
    }

}
