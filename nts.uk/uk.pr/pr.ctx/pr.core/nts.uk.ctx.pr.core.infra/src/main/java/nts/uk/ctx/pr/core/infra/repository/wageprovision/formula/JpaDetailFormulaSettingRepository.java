package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSettingRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtDetailCalculationFormula;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtDetailFormulaSetting;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtDetailFormulaSettingPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class JpaDetailFormulaSettingRepository extends JpaRepository implements DetailFormulaSettingRepository {
    private static final String SELECT_DETAIL_SET_BY_KEY = "SELECT f FROM QpbmtDetailFormulaSetting f WHERE f.detailFormulaSetPk.historyID =:historyID";
    private static final String SELECT_DETAIL_CALCULATION_BY_KEY = "SELECT f FROM QpbmtDetailCalculationFormula f WHERE f.detailCalculationFormulaPk.historyID =:historyID ORDER BY f.detailCalculationFormulaPk.elementOrder";

    private static final String REMOVE_SETTING_BY_HISTORY = "DELETE FROM QpbmtDetailFormulaSetting f WHERE f.detailFormulaSetPk.historyID =:historyID ";
    private static final String REMOVE_DETAIL_CALCULATION_BY_HISTORY = "DELETE FROM QpbmtDetailCalculationFormula f WHERE f.detailCalculationFormulaPk.historyID =:historyID ";

    @Override
    public Optional<DetailFormulaSetting> getDetailFormulaSettingById(String historyID){
        Optional<QpbmtDetailFormulaSetting> detailFormulaSetting = this.queryProxy().query(SELECT_DETAIL_SET_BY_KEY, QpbmtDetailFormulaSetting.class)
                .setParameter("historyID", historyID).getSingle();
        List<QpbmtDetailCalculationFormula> detailCalculationFormula = this.queryProxy().query(SELECT_DETAIL_CALCULATION_BY_KEY, QpbmtDetailCalculationFormula.class)
                .setParameter("historyID", historyID).getList();
        return this.toDomain(detailFormulaSetting, detailCalculationFormula);
    }

    @Override
    public void upsert(DetailFormulaSetting domain){
        this.removeByHistory(domain.getHistoryId());
        this.commandProxy().insert(QpbmtDetailFormulaSetting.toEntity(domain.getFormulaCode().v(), domain));
        this.commandProxy().insertAll(QpbmtDetailCalculationFormula.toEntity(domain));
    }

    @Override
    public void removeByHistory(String historyID) {
        this.getEntityManager().createQuery(REMOVE_SETTING_BY_HISTORY).setParameter("historyID", historyID).executeUpdate();
        this.getEntityManager().createQuery(REMOVE_DETAIL_CALCULATION_BY_HISTORY).setParameter("historyID", historyID).executeUpdate();
    }

    private Optional<DetailFormulaSetting> toDomain (Optional<QpbmtDetailFormulaSetting> detailSetting, List<QpbmtDetailCalculationFormula> detailCalculationFormula) {
        if (!detailSetting.isPresent()) return Optional.empty();
        return QpbmtDetailFormulaSetting.toDomain(detailSetting.get(), detailCalculationFormula);
    }

}
