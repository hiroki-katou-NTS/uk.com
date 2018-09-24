package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHisRepository;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccIsHis;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccIsHisPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;


@Stateless
public class JpaOccAccIsHisRepository extends JpaRepository implements OccAccIsHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccIsHis f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsHisPk.cid =:cid ORDER BY f.startYearMonth DESC";

    @Override
    public Optional<OccAccIsHis> getAllOccAccIsHisByCid(String cid) {
        List<QpbmtOccAccIsHis> qpbmtOccAccIsHisList = this.queryProxy().query(SELECT_BY_CID, QpbmtOccAccIsHis.class).setParameter("cid", cid).
                getList();
        return Optional.of(new OccAccIsHis(cid,toDomain(qpbmtOccAccIsHisList)));
    }

    @Override
    public void add(YearMonthHistoryItem domain, String cId) {
        this.commandProxy().insert(QpbmtOccAccIsHis.toEntity(domain,cId));
    }

    @Override
    public void update(YearMonthHistoryItem domain, String cId) {
        this.commandProxy().update(QpbmtOccAccIsHis.toEntity(domain,cId));
    }

    private List<YearMonthHistoryItem> toDomain(List<QpbmtOccAccIsHis> entities) {
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<YearMonthHistoryItem>();
        if (entities == null || entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }

        entities.forEach(entity -> {
            yearMonthHistoryItemList.add( new YearMonthHistoryItem(entity.occAccIsHisPk.hisId,
                    new YearMonthPeriod(new YearMonth(entity.startYearMonth),
                            new YearMonth(entity.endYearMonth))));
        });
        return yearMonthHistoryItemList;
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtOccAccIsHis.class, new QpbmtOccAccIsHisPk(cid, hisId));
    }
}
