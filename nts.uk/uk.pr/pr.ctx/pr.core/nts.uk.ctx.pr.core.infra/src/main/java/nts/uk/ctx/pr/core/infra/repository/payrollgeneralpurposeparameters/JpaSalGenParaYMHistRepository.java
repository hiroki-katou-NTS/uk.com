package nts.uk.ctx.pr.core.infra.repository.payrollgeneralpurposeparameters;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenParaYmHis;
import nts.uk.ctx.pr.core.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenParaYmHisPk;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaYMHistRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaYearMonthHistory;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSalGenParaYMHistRepository extends JpaRepository implements SalGenParaYMHistRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalGenParaYmHis f";
    private static final String SELECT_ALL_QUERY_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParaYmHisPk.paraNo =:paraNo AND  f.salGenParaYmHisPk.cid =:cid ORDER BY f.startYearMonth DESC ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParaYmHisPk.paraNo =:paraNo AND  f.salGenParaYmHisPk.cid =:cid AND  f.salGenParaYmHisPk.hisId =:hisId ";

    @Override
    public Optional<SalGenParaYearMonthHistory> getAllSalGenParaYMHist(String cid, String paraNo) {
        List<QpbmtSalGenParaYmHis> entities = this.queryProxy().query(SELECT_ALL_QUERY_BY_CID, QpbmtSalGenParaYmHis.class)
                .setParameter("paraNo", paraNo)
                .setParameter("cid", cid)
                .getList();
         return Optional.of(new SalGenParaYearMonthHistory(paraNo,cid,toDomain(entities)));

    }

    @Override
    public Optional<SalGenParaYearMonthHistory> getSalGenParaYMHistById(String paraNo, String cid, String hisId){
        List<QpbmtSalGenParaYmHis> entities = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalGenParaYmHis.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .setParameter("hisId", hisId)
        .getList();
        return Optional.of(new SalGenParaYearMonthHistory(paraNo,cid,toDomain(entities)));

    }

    @Override
    public void add(YearMonthHistoryItem domain, String cId,String paraNo){
        this.commandProxy().insert(QpbmtSalGenParaYmHis.toEntity(domain,cId,paraNo));
    }

    @Override
    public void update(YearMonthHistoryItem domain, String cId,String paraNo){
        this.commandProxy().update(QpbmtSalGenParaYmHis.toEntity(domain,cId,paraNo));
    }

    @Override
    public void remove(String paraNo, String cid, String hisId){
        this.commandProxy().remove(QpbmtSalGenParaYmHis.class, new QpbmtSalGenParaYmHisPk(paraNo, cid, hisId));
    }
    private List<YearMonthHistoryItem> toDomain(List<QpbmtSalGenParaYmHis> entities) {
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<YearMonthHistoryItem>();
        if (entities == null || entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }

        entities.forEach(entity -> {
            yearMonthHistoryItemList.add( new YearMonthHistoryItem(entity.salGenParaYmHisPk.hisId,
                    new YearMonthPeriod(new YearMonth(entity.startYearMonth),
                            new YearMonth(entity.endYearMonth))));
        });
        return yearMonthHistoryItemList;
    }
}
