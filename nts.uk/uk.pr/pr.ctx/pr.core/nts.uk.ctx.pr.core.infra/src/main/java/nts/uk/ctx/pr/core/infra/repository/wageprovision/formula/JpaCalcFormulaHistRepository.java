package nts.uk.ctx.pr.core.infra.repository.wageprovision.formula;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHist;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaHistRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.formula.QpbmtFormulaHist;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaCalcFormulaHistRepository extends JpaRepository implements FormulaHistRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtFormulaHist f";
    private static final String SELECT_BY_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.formulaHistPk.cid =:cid AND" +
            " f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth ";

    @Override
    public List<FormulaHist> getFormulaHistByYearMonth(String cid, YearMonth yearMonth) {
        return QpbmtFormulaHist.toDomain(this.queryProxy().query(SELECT_BY_YM, QpbmtFormulaHist.class)
                .setParameter("cid", cid)
                .setParameter("yearMonth", yearMonth.v())
                .getList());
    }

}
