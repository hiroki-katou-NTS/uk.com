package nts.uk.ctx.pr.shared.infra.repository.payrollgeneralpurposeparameters;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistory;
import nts.uk.ctx.pr.shared.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenPrDateHis;
import nts.uk.ctx.pr.shared.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenPrDateHisPk;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaSalGenParaDateHistRepository extends JpaRepository implements SalGenParaDateHistRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalGenPrDateHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrDateHisPk.paraNo =:paraNo AND  f.salGenPrDateHisPk.cid =:cid AND  f.salGenPrDateHisPk.hisId =:hisId ";

    @Override
    public List<SalGenParaDateHistory> getAllSalGenParaDateHist(){
        return new ArrayList<SalGenParaDateHistory>();
    }

    @Override
    public Optional<SalGenParaDateHistory> getSalGenParaDateHistById(String paraNo, String cid, String hisId){
        List<QpbmtSalGenPrDateHis> entities = this.queryProxy().query(SELECT_BY_KEY_STRING,QpbmtSalGenPrDateHis.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .setParameter("hisId", hisId)
        .getList();
         return Optional.of(new SalGenParaDateHistory(paraNo,cid,toDomain(entities)));
    }

    @Override
    public void add(DateHistoryItem domain,String paraNo,String cId){
        this.commandProxy().insert(QpbmtSalGenPrDateHis.toEntity(domain,paraNo,cId));
    }



    @Override
    public void update(DateHistoryItem domain,String paraNo,String cId){
        this.commandProxy().update(QpbmtSalGenPrDateHis.toEntity(domain,paraNo,cId));
    }

    @Override
    public void remove(String paraNo, String cid, String hisId){
        this.commandProxy().remove(QpbmtSalGenPrDateHis.class, new QpbmtSalGenPrDateHisPk(paraNo, cid, hisId));
    }

    private List<DateHistoryItem> toDomain(List<QpbmtSalGenPrDateHis> entities) {
        List<DateHistoryItem> yearMonthHistoryItemList = new ArrayList<DateHistoryItem>();
        if (entities == null || entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }

        entities.forEach(entity -> {
            yearMonthHistoryItemList.add( new DateHistoryItem(entity.salGenPrDateHisPk.hisId,
                    new DatePeriod(entity.startDate,
                            entity.endDate)));
        });
        return yearMonthHistoryItemList;
    }

}
