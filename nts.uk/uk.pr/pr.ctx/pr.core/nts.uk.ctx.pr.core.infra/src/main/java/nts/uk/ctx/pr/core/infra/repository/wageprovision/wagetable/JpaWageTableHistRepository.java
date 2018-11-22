package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHist;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTableHist;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaWageTableHistRepository extends JpaRepository implements WageTableHistRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtWageTableHist f";
    private static final String SELECT_BY_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.wageTblHistPk.cid =:cid AND" +
            " f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth ";

    @Override
    public List<WageTableHist> getWageTableHistByYearMonth(String cid, YearMonth yearMonth) {
        return QpbmtWageTableHist.toDomain(this.queryProxy().query(SELECT_BY_YM, QpbmtWageTableHist.class)
                .setParameter("cid", cid)
                .setParameter("yearMonth", yearMonth.v())
                .getList());
    }

}
