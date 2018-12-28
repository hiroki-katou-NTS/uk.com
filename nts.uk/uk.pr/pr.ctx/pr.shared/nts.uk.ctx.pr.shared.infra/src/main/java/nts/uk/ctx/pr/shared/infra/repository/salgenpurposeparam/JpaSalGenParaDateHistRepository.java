package nts.uk.ctx.pr.shared.infra.repository.salgenpurposeparam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaDateHistRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaDateHistory;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenPrDateHis;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenPrDateHisPk;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaSalGenParaDateHistRepository extends JpaRepository implements SalGenParaDateHistRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSalGenPrDateHis f";
    private static final String SELECT_BY_KEY_PARANO = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrDateHisPk.paraNo =:paraNo AND  f.salGenPrDateHisPk.cid =:cid ORDER BY f.startDate DESC  ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrDateHisPk.paraNo =:paraNo AND  f.salGenPrDateHisPk.cid =:cid AND  f.salGenPrDateHisPk.hisId =:hisId ";

    @Override
    public Optional<SalGenParaDateHistory> getAllSalGenParaDateHist(String cid,String paraNo){
        List<QqsmtSalGenPrDateHis> entities = this.queryProxy().query(SELECT_BY_KEY_PARANO,QqsmtSalGenPrDateHis.class)
                .setParameter("paraNo", paraNo)
                .setParameter("cid", cid)
                .getList();
        return Optional.of(new SalGenParaDateHistory(paraNo,cid,toDomain(entities)));
    }

    @Override
    public Optional<SalGenParaDateHistory> getSalGenParaDateHistById(String paraNo, String cid, String hisId){
        List<QqsmtSalGenPrDateHis> entities = this.queryProxy().query(SELECT_BY_KEY_STRING,QqsmtSalGenPrDateHis.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .setParameter("hisId", hisId)
        .getList();
         return Optional.of(new SalGenParaDateHistory(paraNo,cid,toDomain(entities)));
    }

    @Override
    public void add(DateHistoryItem domain,String paraNo,String cId){
        this.commandProxy().insert(QqsmtSalGenPrDateHis.toEntity(domain,paraNo,cId));
    }



    @Override
    public void update(DateHistoryItem domain,String paraNo,String cId){
        this.commandProxy().update(QqsmtSalGenPrDateHis.toEntity(domain,paraNo,cId));
    }

    @Override
    public void remove(String paraNo, String cid, String hisId){
        this.commandProxy().remove(QqsmtSalGenPrDateHis.class, new QqsmtSalGenPrDateHisPk(paraNo, cid, hisId));
    }

    private List<DateHistoryItem> toDomain(List<QqsmtSalGenPrDateHis> entities) {
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
